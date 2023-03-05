package byx.script.server.controller;

import byx.script.core.ByxScriptRunner;
import byx.script.server.dto.OutputDto;
import byx.script.server.dto.RunRequestDto;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.*;

@RestController
@Validated
@Slf4j
public class ByxScriptController {
    private static final ExecutorService EXECUTORS = Executors.newFixedThreadPool(10);

    @PostMapping("run")
    public OutputDto run(@RequestBody @Validated RunRequestDto request) {
        OutputDto dto = new OutputDto();
        dto.setOutput(runScript(request.getScript(), request.getInput()));
        return dto;
    }

    private String runScript(String script, String input) {
        Future<String> future = EXECUTORS.submit(() -> {
            try (
                    ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    Scanner scanner = new Scanner(is);
                    PrintStream printStream = new PrintStream(os)
            ) {
                ByxScriptRunner runner = new ByxScriptRunner(scanner, printStream);
                log.info("run script begin: {}", script);
                runner.run(script);
                log.info("run script end: {}", script);
                return os.toString();
            } catch (IOException e) {
                log.error("IO exception: {}", e.getMessage());
                return "ERROR: " + e.getMessage();
            } catch (Exception e) {
                log.error("unknown exception: {}", e.getMessage());
                return "ERROR: " + e.getMessage();
            }
        });

        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            log.error("future exception: {}", e.getMessage());
            return "ERROR: " + e.getMessage();
        } catch (TimeoutException e) {
            future.cancel(true);
            return "运行超时";
        }
    }
}
