
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TurnBurn extends SplitCard {

    public TurnBurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}", "{1}{R}", SpellAbilityType.SPLIT_FUSED);

        // Turn
        // Until end of turn, target creature loses all abilities and becomes a red Weird with base power and toughness 0/1.
        Effect effect = new BecomesCreatureTargetEffect(new WeirdToken(), true, false, Duration.EndOfTurn)
                .withDurationRuleAtStart(true);
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("becomes a Weird"));

        // Burn
        // Burn deals 2 damage to any target.
        effect = new DamageTargetEffect(2);
        effect.setText("Burn deals 2 damage to any target");
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("2 damage"));

    }

    private TurnBurn(final TurnBurn card) {
        super(card);
    }

    @Override
    public TurnBurn copy() {
        return new TurnBurn(this);
    }

    private static class WeirdToken extends TokenImpl {

        private WeirdToken() {
            super("Weird", "red Weird with base power and toughness 0/1");
            cardType.add(CardType.CREATURE);
            color.setRed(true);
            subtype.add(SubType.WEIRD);
            power = new MageInt(0);
            toughness = new MageInt(1);
        }
        public WeirdToken(final WeirdToken token) {
            super(token);
        }

        public WeirdToken copy() {
            return new WeirdToken(this);
        }

    }

}
