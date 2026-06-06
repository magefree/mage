package mage.cards.b;

import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BonecrusherGiant extends AdventureCard {

    public BonecrusherGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT}, "{2}{R}",
                "Stomp",
                new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Bonecrusher Giant
        this.getLeftHalfCard().setPT(4, 3);

        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell's controller.
        this.getLeftHalfCard().addAbility(new BecomesTargetSourceTriggeredAbility(
                new DamageTargetEffect(2).withTargetDescription("that spell's controller"),
                StaticFilters.FILTER_SPELL_A, SetTargetPointer.PLAYER, false)
                .withRuleTextReplacement(false));

        // Stomp
        // Damage can't be prevented this turn. Stomp deals 2 damage to any target.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        finalizeCard();
    }

    private BonecrusherGiant(final BonecrusherGiant card) {
        super(card);
    }

    @Override
    public BonecrusherGiant copy() {
        return new BonecrusherGiant(this);
    }
}
