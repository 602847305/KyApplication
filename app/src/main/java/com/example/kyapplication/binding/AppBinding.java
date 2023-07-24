package com.example.kyapplication.binding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class AppBinding {

    @BindingAdapter({"adapter"})
    public static final void adapter(@NotNull RecyclerView $this$adapter, @NotNull RecyclerView.Adapter adapter) {
        Intrinsics.checkParameterIsNotNull($this$adapter, "$this$adapter");
        Intrinsics.checkParameterIsNotNull(adapter, "adapter");
        $this$adapter.setAdapter(adapter);
    }
}
