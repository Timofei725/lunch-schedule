package ru.kiselev.lunchschedule.utill;

import lombok.experimental.UtilityClass;

import ru.kiselev.lunchschedule.HasId;
import ru.kiselev.lunchschedule.error.IllegalRequestDataException;


@UtilityClass
public class ValidationUtil {
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }




}
