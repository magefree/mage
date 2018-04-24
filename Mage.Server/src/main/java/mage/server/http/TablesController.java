package mage.server.http;

import mage.game.Table;
import mage.server.TableManager;
import mage.view.TableView;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/tables")
public class TablesController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<TableView> index(@RequestHeader(value="Authorization") String jwt) {
        List<TableView> list = new ArrayList<TableView>();

        for(Table tbl: TableManager.instance.getTables()) {
            list.add(new TableView(tbl));
        }

        return list;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TableView show(@PathVariable String id,
                          @RequestHeader(value="Authorization") String jwt) {
        return new TableView(TableManager.instance.getTable(UUID.fromString(id)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public TableView delete(@PathVariable String id,
                            @RequestHeader(value="Authorization") String jwt) {
        return new TableView(TableManager.instance.getTable(UUID.fromString(id)));
    }
}
