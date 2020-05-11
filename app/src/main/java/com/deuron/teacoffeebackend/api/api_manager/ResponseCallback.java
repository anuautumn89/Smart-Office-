package com.deuron.teacoffeebackend.api.api_manager;
import org.jetbrains.annotations.Nullable;

public interface ResponseCallback<T> {
    void onSuccess(T response);
    //void onFailure(T errorResponse);
    void onFailure(@Nullable String message);
}
