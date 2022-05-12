
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class AtarkasCommand extends CardImpl {

    public AtarkasCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Your opponents can't gain life this turn;
        this.getSpellAbility().addEffect(new CantGainLifeAllEffect(Duration.EndOfTurn, TargetController.OPPONENT));

        // or Atarka's Command deals 3 damage to each opponent;
        Mode mode = new Mode(new DamagePlayersEffect(3, TargetController.OPPONENT));
        this.getSpellAbility().addMode(mode);

        // or You may put a land card from your hand onto the battlefield;
        mode = new Mode(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A));
        this.getSpellAbility().addMode(mode);

        // or Creatures you control get +1/+1 and gain reach until the end of turn.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +1/+1");
        mode = new Mode(effect);
        effect = new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gain reach until the end of turn");
        mode.addEffect(effect);
        this.getSpellAbility().addMode(mode);

    }

    private AtarkasCommand(final AtarkasCommand card) {
        super(card);
    }

    @Override
    public AtarkasCommand copy() {
        return new AtarkasCommand(this);
    }
}
