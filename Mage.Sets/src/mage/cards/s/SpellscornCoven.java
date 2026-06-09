package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellscornCoven extends AdventureCard {

    public SpellscornCoven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.WARLOCK}, "{3}{B}",
                "Take It Back",
                new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Spellscorn Coven
        this.getLeftHalfCard().setPT(2, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Spellscorn Coven enters the battlefield, each opponent discards a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));

        // Take it Back
        // Return target spell to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetSpell());

        finalizeCard();
    }

    private SpellscornCoven(final SpellscornCoven card) {
        super(card);
    }

    @Override
    public SpellscornCoven copy() {
        return new SpellscornCoven(this);
    }
}
