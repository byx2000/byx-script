package byx.script.server.controller;

import byx.script.core.ByxScriptRunner;
import byx.script.server.dto.OutputDto;
import byx.script.server.dto.RunRequestDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
public class ByxScriptController {
    private static final ByxScriptRunner runner = new ByxScriptRunner();

    @PostMapping("run")
    public OutputDto run(@RequestBody @Validated RunRequestDto request) {
        OutputDto dto = new OutputDto();
        dto.setOutput(runScript(request.getScript(), request.getInput()));
        return dto;
    }

    private String runScript(String script, String input) {
        CompletableFuture<Object> future = CompletableFuture.anyOf(CompletableFuture.supplyAsync(() -> {
            return getScriptOutput(script, input);
        }), CompletableFuture.supplyAsync(() -> {
            return TimeoutGuard();
        })).exceptionally(t -> {
            return "ERROR: " + t.getCause().getMessage();
        });

        return (String) future.join();
    }

    private String getScriptOutput(String script, String input) {
        try (
                ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Scanner scanner = new Scanner(is);
                PrintStream printStream = new PrintStream(os)
        ) {
            ByxScriptRunner runner = new ByxScriptRunner(scanner, printStream);
            runner.run(script);
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String TimeoutGuard() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Time limit exceed";
    }
}
