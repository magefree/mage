package mage.cards.r;

import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagingKronch extends CardImpl {

    public RagingKronch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Raging Kronch can't attack alone.
        this.addAbility(new CantAttackAloneAbility());
    }

    private RagingKronch(final RagingKronch card) {
        super(card);
    }

    @Override
    public RagingKronch copy() {
        return new RagingKronch(this);
    }
}
