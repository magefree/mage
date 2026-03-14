package mage.cards.o;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrderOfTheMirror extends TransformingDoubleFacedCard {

    public OrderOfTheMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{U}",
                "Order of the Alabaster Host",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.KNIGHT}, "WU");

        // Order of the Mirror
        this.getLeftHalfCard().setPT(2, 1);

        // {3}{W/P}: Transform Order of the Mirror. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{W/P}")));

        // Order of the Alabaster Host
        this.getRightHalfCard().setPT(3, 3);

        // Whenever Order of the Alabaster Host becomes blocked by a creature, that creature gets -1/-1 until end of turn.
        this.getRightHalfCard().addAbility(new BecomesBlockedByCreatureTriggeredAbility(new BoostTargetEffect(-1, -1).setText("the blocking creature gets -1/-1 until end of turn"), false));
    }

    private OrderOfTheMirror(final OrderOfTheMirror card) {
        super(card);
    }

    @Override
    public OrderOfTheMirror copy() {
        return new OrderOfTheMirror(this);
    }
}
