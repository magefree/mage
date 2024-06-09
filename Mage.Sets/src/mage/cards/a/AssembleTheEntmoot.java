package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SylvanOfferingTreefolkToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AssembleTheEntmoot extends CardImpl {

    public AssembleTheEntmoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");


        // Creatures you control have reach.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        ReachAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                )
        ));

        // Sacrifice Assemble the Entmoot: Create three tapped X/X green Treefolk creature tokens, where X is the amount of life you gained this turn. Put a reach counter on each of them.
        Ability ability = new SimpleActivatedAbility(
                new AssembleTheEntmootEffect(),
                new SacrificeSourceCost()
        );
        ability.addHint(ControllerGainedLifeCount.getHint());
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.addAbility(ability);
    }

    private AssembleTheEntmoot(final AssembleTheEntmoot card) {
        super(card);
    }

    @Override
    public AssembleTheEntmoot copy() {
        return new AssembleTheEntmoot(this);
    }
}

class AssembleTheEntmootEffect extends OneShotEffect {

    AssembleTheEntmootEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create three tapped X/X green Treefolk creature tokens, where X is the "
                + "amount of life you gained this turn. Put a reach counter on each of them.";
    }

    private AssembleTheEntmootEffect(final AssembleTheEntmootEffect effect) {
        super(effect);
    }

    @Override
    public AssembleTheEntmootEffect copy() {
        return new AssembleTheEntmootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (controllerId == null || watcher == null) {
            return false;
        }

        int xValue = watcher.getLifeGained(controllerId);

        CreateTokenEffect effect = new CreateTokenEffect(
                new SylvanOfferingTreefolkToken(xValue),
                3, true
        );
        effect.apply(game, source);

        for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(addedTokenId);
            if (token != null) {
                token.addCounters(
                        CounterType.REACH.createInstance(),
                        controllerId, source, game
                );
            }
        }

        return true;
    }

}