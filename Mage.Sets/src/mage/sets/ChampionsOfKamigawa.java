package mage.sets;

import mage.Constants;
import mage.cards.ExpansionSet;

import java.util.GregorianCalendar;

public class ChampionsOfKamigawa extends ExpansionSet {
    private static final ChampionsOfKamigawa fINSTANCE =  new ChampionsOfKamigawa();

	public static ChampionsOfKamigawa getInstance() {
		return fINSTANCE;
	}

    private ChampionsOfKamigawa() {
        super("Champions of Kamigawa", "CHK", "", "mage.sets.championsofkamigawa",  new GregorianCalendar(2004, 9, 1).getTime(), Constants.SetType.EXPANSION);
        this.blockName = "Kamigawa";
		this.hasBoosters = true;
		this.numBoosterLands = 1;
		this.numBoosterCommon = 10;
		this.numBoosterUncommon = 3;
		this.numBoosterRare = 1;
		this.ratioBoosterMythic = 8;
    }

}
