package ru.inno.todo.okhttp;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        System.out.println("===== REQUEST =====");
        Request request = chain.request();
        System.out.println(request.method() + " " + request.url());
        Map<String, List<String>> headers = request.headers().toMultimap();
        for (String key : headers.keySet()) {
            for (String value : headers.get(key)) {
                System.out.println(key + " : " + value);
            }
        }
        if (request.method().equals("POST") || request.method().equals("PATCH")) {
            // TODO: read req body
        }

        Response response = chain.proceed(request);
        System.out.println("===== RESPONSE =====");
        System.out.println(response.code());
        Map<String, List<String>> respHeaders = response.headers().toMultimap();
        for (String key : respHeaders.keySet()) {
            for (String value : respHeaders.get(key)) {
                System.out.println(key + " : " + value);
            }
        }

        long length = Long.parseLong(Objects.requireNonNull(response.header("Content-Length")));
        if (length > 0) {
            System.out.println("BODY:");

            String s = response.peekBody(length).string();
            System.out.println(s);
        } else {
            System.out.println("NO BODY");
        }

        return response;
    }
}
