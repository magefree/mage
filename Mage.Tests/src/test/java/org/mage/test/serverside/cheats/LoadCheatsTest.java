package org.mage.test.serverside.cheats;

import mage.constants.*;
import mage.utils.SystemUtil;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author JayDi85
 */
public class LoadCheatsTest extends CardTestPlayerBase {

    private String prepareCommandFile(String data){
        try {
            File commandFile = File.createTempFile("test-commands", ".txt");
            FileWriter w = new FileWriter(commandFile);
            w.write(data);
            w.close();
            return commandFile.getAbsolutePath();
        }catch (IOException e) {
            Assert.fail("Can't create commands file: " + e.getMessage());
            return null;
        }
    }

    @Test
    public void testCommands(){
        addCard(Zone.HAND, playerA, "Razorclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        String commandsFile = prepareCommandFile(new StringBuilder()
                .append("[group 1]").append('\n') // 1 group
                .append("battlefield:Human:Forest:10").append('\n')
                .append("//battlefield:Human:Forest:10").append('\n')
                .append("battlefield:Human:Island:10").append('\n')
                .append("").append('\n')
                .append("[@mana add]").append('\n') // 2 group
                .append("[group 2]").append('\n') // 3 group
                .append("//").append('\n')
                .append("hand:Human:Island:10").append('\n') // need that from 3 group
                .append("[group 3]").append('\n')
                .append("//").append('\n')
                .append("hand:Human:Island:5").append('\n')
                .append("[group 4]").append('\n') // 4 group
                .append("hand:Human:Island:3").append('\n')
                .toString()
                .replace(":Human:", ":" + playerA.getName() + ":")
        );

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        setChoice(playerA, "5"); // choose [group 3]: 5 = 2 default menus + 3 group
        SystemUtil.executeCheatCommands(currentGame, commandsFile, playerA);

        assertHandCount(playerA, "Razorclaw Bear", 1);
        assertPermanentCount(playerA, "Mountain", 3);
        assertHandCount(playerA, "Island", 10); // by cheats
    }
}
