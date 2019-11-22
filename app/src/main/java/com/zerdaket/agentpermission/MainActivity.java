package com.zerdaket.agentpermission;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zerdaket.agent.permission.AgentPermission;
import com.zerdaket.agent.permission.listener.Rationale;
import com.zerdaket.agent.permission.listener.Result;
import com.zerdaket.agent.permission.request.Requester;
import com.zerdaket.agentpermission.bean.PermissionEnum;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionAdapter adapter = new PermissionAdapter();
        adapter.setPermissionEnums(Arrays.asList(PermissionEnum.values()));
        adapter.setOnItemClickListener(new PermissionAdapter.OnItemClickListener() {
            @Override
            public void onClick(PermissionEnum permissionEnum, int position) {
                checkPermission(permissionEnum.getCode());
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        Button installBtn = findViewById(R.id.btn_install);
        installBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgentPermission.with(MainActivity.this)
                        .install()
                        .rationale(new Rationale<Void>() {
                            @Override
                            public void showRationale(Void data, @NonNull final Requester requester) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage("开启权限才能使用安装")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requester.execute();
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .create()
                                        .show();
                            }
                        })
                        .onGranted(new Result<Void>() {
                            @Override
                            public void onResult(Void data) {
                                Toast.makeText(MainActivity.this, "获取安装权限成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onDenied(new Result<Void>() {
                            @Override
                            public void onResult(Void data) {
                                Toast.makeText(MainActivity.this, "获取安装权限失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();
            }
        });

        Button button = findViewById(R.id.btn_multiply);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgentPermission.with(MainActivity.this)
                        .runtime()
                        .permission(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                        .onGranted(new Result<List<String>>() {
                            @Override
                            public void onResult(List<String> permissions) {
                                Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onDenied(new Result<List<String>>() {
                            @Override
                            public void onResult(List<String> permissions) {
                                Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();
            }
        });
    }

    private void checkPermission(String permission) {
        AgentPermission.with(this)
                .runtime()
                .permission(permission)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(List<String> permissions, @NonNull final Requester requester) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("开启权限才能使用该功能")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requester.execute();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }
                })
                .onGranted(new Result<List<String>>() {
                    @Override
                    public void onResult(List<String> permissions) {
                        Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Result<List<String>>() {
                    @Override
                    public void onResult(List<String> permissions) {
                        Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    private static class PermissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<PermissionEnum> mPermissionEnums;
        private OnItemClickListener mListener;

        void setPermissionEnums(List<PermissionEnum> permissionEnums) {
            mPermissionEnums = permissionEnums;
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_permission, parent, false);
            return new PermissionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            PermissionViewHolder viewHolder = (PermissionViewHolder) holder;
            viewHolder.bind(mPermissionEnums.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mPermissionEnums == null ? 0 : mPermissionEnums.size();
        }

        class PermissionViewHolder extends RecyclerView.ViewHolder {

            private Button button;

            PermissionViewHolder(@NonNull View itemView) {
                super(itemView);
                button = itemView.findViewById(R.id.button);
            }

            void bind(final PermissionEnum permissionEnum, final int position) {
                button.setText(permissionEnum.getName());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener == null) return;
                        mListener.onClick(permissionEnum, position);
                    }
                });

            }
        }

        public interface OnItemClickListener {
            void onClick(PermissionEnum permissionEnum, int position);
        }

    }
}
