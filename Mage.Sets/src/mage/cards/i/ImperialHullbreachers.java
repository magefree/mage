package mage.cards.i;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class ImperialHullbreachers extends CardImpl {

    public ImperialHullbreachers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squad {2}
        this.addAbility(new SquadAbility(new GenericManaCost(2)));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Battle cry
        this.addAbility(new BattleCryAbility());
    }

    private ImperialHullbreachers(final ImperialHullbreachers card) {
        super(card);
    }

    @Override
    public ImperialHullbreachers copy() {
        return new ImperialHullbreachers(this);
    }
}
