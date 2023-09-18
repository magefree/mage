package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.EnlistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoalitionWarbrute extends CardImpl {

    public CoalitionWarbrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Enlist
        this.addAbility(new EnlistAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private CoalitionWarbrute(final CoalitionWarbrute card) {
        super(card);
    }

    @Override
    public CoalitionWarbrute copy() {
        return new CoalitionWarbrute(this);
    }
}
