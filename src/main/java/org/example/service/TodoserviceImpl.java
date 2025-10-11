package main.java.org.example.service;

import main.java.org.example.dao.TodoDao;
import main.java.org.example.model.Todo;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Optional;

public class TodoserviceImpl implements Todoservice{

    private TodoDao todoDao;

    public TodoserviceImpl(TodoDao todoDao) {
        this.todoDao = todoDao;
    }


    @Override
    public Optional<Todo> create(Todo todo) throws SQLException, SQLTimeoutException {
        if (todo == null){
            throw new IllegalArgumentException("할 일 정보가 없습니다.");
        }
        if(todo.getTitle() == null|| todo.getTitle().isEmpty()){
            throw new IllegalArgumentException("제목은 필수 입력 칸입니다.");
        }
        return todoDao.create(todo);
    }

    @Override
    public Optional<Todo> findById(long id) throws SQLException, SQLTimeoutException {
        if(todoDao.findById(id) == null || todoDao.findById(id).isEmpty()){
            throw new IllegalArgumentException ("잘못된 할 일 ID입니다.");
        }
        return todoDao.findById(id);
    }

    @Override
    public boolean update(Todo todo) throws SQLException, SQLTimeoutException {
        if(todo == null || todo.getId() <= 0){
            throw new IllegalArgumentException("수정할 todo 정보가 유효하지 않습니다.");
        }

        Optional<Todo> existing = todoDao.findById(todo.getId());
        if(existing.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 TODO입니다.");
        }
        return todoDao.update(todo);
    }

    @Override
    public boolean delete(long id) throws SQLException, SQLTimeoutException {
        if(id <= 0){
            throw new IllegalArgumentException("삭제할 Todo ID가 올바르지 않습니다.");
        }
        return todoDao.delete(id);

    }
}
