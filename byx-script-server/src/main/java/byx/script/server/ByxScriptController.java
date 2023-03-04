package byx.script.server;

import byx.script.core.ByxScriptRunner;
import byx.script.core.util.OutputUtils;
import byx.script.server.dto.OutputDto;
import byx.script.server.dto.RunRequestDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            return OutputUtils.getOutput(input, () -> runner.run(script));
        }), CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Time limit exceed";
        })).exceptionally(t -> {
            return "ERROR: " + t.getCause().getMessage();
        });

        return (String) future.join();
    }
}
