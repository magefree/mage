package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachineCommander extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Blight Titan", "Brimaz, Blight of Oreskos", "Excise the Imperfect");

    private static final MarchOfTheMachineCommander instance = new MarchOfTheMachineCommander();

    public static MarchOfTheMachineCommander getInstance() {
        return instance;
    }

    private MarchOfTheMachineCommander() {
        super("March of the Machine Commander", "MOC", ExpansionSet.buildDate(2023, 4, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Goro-Goro and Satoru", 445, Rarity.MYTHIC, mage.cards.g.GoroGoroAndSatoru.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }
}
