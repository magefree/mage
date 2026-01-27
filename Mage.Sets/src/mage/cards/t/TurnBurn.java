
package mage.cards.t;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TurnBurn extends SplitCard {

    public TurnBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}", "{1}{R}", SpellAbilityType.SPLIT_FUSED);

        // Turn
        // Until end of turn, target creature loses all abilities and becomes a red Weird with base power and toughness 0/1.
        Effect effect = new BecomesCreatureTargetEffect(
            new CreatureToken(0, 1, "red Weird with base power and toughness 0/1", SubType.WEIRD).withColor("R"),
            true,
            false,
            Duration.EndOfTurn
        ).withDurationRuleAtStart(true);
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("becomes a Weird"));

        // Burn
        // Burn deals 2 damage to any target.
        getRightHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("2 damage"));

    }

    private TurnBurn(final TurnBurn card) {
        super(card);
    }

    @Override
    public TurnBurn copy() {
        return new TurnBurn(this);
    }
}
