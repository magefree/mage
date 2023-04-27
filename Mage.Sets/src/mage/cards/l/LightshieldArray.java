package mage.cards.l;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightshieldArray extends CardImpl {

    public LightshieldArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setWhite(true);
        this.nightCard = true;

        // At the beginning of your end step, put a +1/+1 counter on each creature that attacked this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LightshieldArrayEffect(), TargetController.YOU, false
        ));

        // Sacrifice Lightshield Array: Creatures you control gain hexproof and indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control gain hexproof"), new SacrificeSourceCost());
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and indestructible until end of turn"));
        this.addAbility(ability);
    }

    private LightshieldArray(final LightshieldArray card) {
        super(card);
    }

    @Override
    public LightshieldArray copy() {
        return new LightshieldArray(this);
    }
}

class LightshieldArrayEffect extends OneShotEffect {

    LightshieldArrayEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature that attacked this turn";
    }

    private LightshieldArrayEffect(final LightshieldArrayEffect effect) {
        super(effect);
    }

    @Override
    public LightshieldArrayEffect copy() {
        return new LightshieldArrayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mor : game
                .getState()
                .getWatcher(AttackedThisTurnWatcher.class)
                .getAttackedThisTurnCreatures()) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
