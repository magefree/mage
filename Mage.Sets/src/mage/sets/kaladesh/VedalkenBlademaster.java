package mage.sets.kaladesh;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

import java.util.UUID;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class VedalkenBlademaster extends CardImpl {

    public VedalkenBlademaster(final UUID ownerId){
        super(ownerId, 68, "Vedalken Blademaster", Rarity.COMMON, new CardType[]{CardType.CREATURE},"{2U}");

        this.expansionSetCode = "KLD";

        this.subtype.add("Vedalken");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new ProwessAbility());


    }

    public VedalkenBlademaster(final VedalkenBlademaster card){
        super(card);
    }

    @Override
    public VedalkenBlademaster copy() {
        return new VedalkenBlademaster(this);
    }
}
