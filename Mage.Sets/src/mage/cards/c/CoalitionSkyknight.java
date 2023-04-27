package mage.cards.c;

import mage.MageInt;
import mage.abilities.keyword.EnlistAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoalitionSkyknight extends CardImpl {

    public CoalitionSkyknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Enlist
        this.addAbility(new EnlistAbility());
    }

    private CoalitionSkyknight(final CoalitionSkyknight card) {
        super(card);
    }

    @Override
    public CoalitionSkyknight copy() {
        return new CoalitionSkyknight(this);
    }
}
