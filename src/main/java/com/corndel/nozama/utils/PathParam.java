package com.corndel.nozama.utils;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class PathParam {
    public static Integer getIntegerOrThrow(Context context, String paramName) {
        return context.pathParamAsClass(paramName, Integer.class)
                .getOrThrow((m) -> {
                    throw new BadRequestResponse();
                });
    }
}
