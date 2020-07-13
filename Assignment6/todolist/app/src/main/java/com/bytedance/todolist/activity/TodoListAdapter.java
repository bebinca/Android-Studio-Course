package com.bytedance.todolist.activity;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {
    private List<TodoListEntity> mDatas;

    Context context;
    private int remainItemCount;

    public TodoListAdapter() {
        mDatas = new ArrayList<>();
    }

    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TodoListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoListItemHolder holder, final int position) {
        holder.bind(mDatas.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {
                final TodoListEntity entity = mDatas.get(position);
                if (isChecked != entity.getChecked()) {
                    new Thread() {
                        @Override
                        public void run() {
                            TodoListDao dao = TodoListDatabase.inst(context).todoListDao();
                            entity.setChecked(isChecked);//
                            dao.update(entity);//
                        }
                    }.start();
                    TodoListDao dao = TodoListDatabase.inst(context).todoListDao();
                    entity.setChecked(isChecked);//
                    dao.update(entity);//
                    removeEntity(position);
                    addEntity(entity);//
                }
            }
        });
        holder.delete.setOnClickListener(new Button.OnClickListener() {
            final TodoListEntity entity = mDatas.get(position);
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(context).todoListDao();
                        dao.delete(entity);
                    }
                }.start();
                removeEntity(position);
            }
        });
    }

    public List<TodoListEntity> finishSort(List<TodoListEntity> list) {
        List<TodoListEntity> finishEntity = new ArrayList<>();
        List<TodoListEntity> notFinishEntity = new ArrayList<>();
        List<TodoListEntity> entityList = new ArrayList<>();
        remainItemCount = 0;
        for (int i = 0; i < list.size(); i++) {
            TodoListEntity entity = list.get(i);

            if (entity.getChecked() == true) {
                finishEntity.add(entity);
            }
            else {
                notFinishEntity.add(entity);
                remainItemCount++;
            }
        }
        for (int i = 0; i < notFinishEntity.size(); i++)
            entityList.add(notFinishEntity.get(i));
        for (int i = 0; i < finishEntity.size(); i++)
            entityList.add(finishEntity.get(i));
        return entityList;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = finishSort(list);
        notifyDataSetChanged();
    }
    @MainThread
    public void removeEntity(int position) {
        if(position < remainItemCount) remainItemCount--;
        mDatas.remove(position);
        notifyDataSetChanged();
    }
    @MainThread
    public void addEntity(TodoListEntity entity) {
        if (entity.getChecked() == true) mDatas.add(remainItemCount,entity);
        else { mDatas.add(0, entity); remainItemCount++;}
        notifyDataSetChanged();
    }
    @MainThread
    public void refresh() {
        notifyDataSetChanged();
    }
}

