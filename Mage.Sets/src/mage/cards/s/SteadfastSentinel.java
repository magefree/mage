package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

public class SteadfastSentinel extends CardImpl {

    public SteadfastSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        subtype.add("Human");
        subtype.add("Cleric");
        power = new MageInt(2);
        toughness = new MageInt(3);

        //Vigilance
        addAbility(VigilanceAbility.getInstance());

        //Eternalize {4}{W}{W}
        addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{W}{W}"), this));
    }


    public SteadfastSentinel(final SteadfastSentinel card) {
        super(card);
    }

    @Override
    public SteadfastSentinel copy() {
        return new SteadfastSentinel(this);
    }
}
