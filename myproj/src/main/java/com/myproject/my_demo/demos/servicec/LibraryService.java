package com.myproject.my_demo.demos.servicec;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.my_demo.demos.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface LibraryService extends IService<Library> {
    Library getByBookname(String string);
}
