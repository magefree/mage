package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Jmlundeen
 */
public final class MomentumBreaker extends CardImpl {

    public MomentumBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this enchantment enters, each opponent sacrifices a creature or Vehicle of their choice. Each opponent who can't discards a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MomentumBreakerEffect()));
        // {2}, Sacrifice this enchantment: You gain life equal to your speed.
        Effect gainLifeEffect = new GainLifeEffect(ControllerSpeedCount.instance).setText("You gain life equal to your speed");
        Ability ability = new SimpleActivatedAbility(gainLifeEffect,new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MomentumBreaker(final MomentumBreaker card) {
        super(card);
    }

    @Override
    public MomentumBreaker copy() {
        return new MomentumBreaker(this);
    }
}

class MomentumBreakerEffect extends OneShotEffect {

    MomentumBreakerEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent sacrifices a creature or Vehicle of their choice. " +
                "Each opponent who can't discards a card.";
    }

    private MomentumBreakerEffect(final MomentumBreakerEffect effect) {
        super(effect);
    }

    @Override
    public MomentumBreakerEffect copy() {
        return new MomentumBreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getOpponents(source.getControllerId()).forEach(opponent -> {
            Player player = game.getPlayer(opponent);
            if (player != null) {
                FilterPermanent filter = new FilterPermanent("creature or Vehicle");
                filter.add(Predicates.or(
                        CardType.CREATURE.getPredicate(),
                        SubType.VEHICLE.getPredicate()
                ));
                filter.add(new ControllerIdPredicate(opponent));
                    Effect effect = new SacrificeEffect(filter, 1, null);
                    effect.setTargetPointer(new FixedTarget(player.getId(), game));
                    if (!effect.apply(game, source)) {
                        player.discard(1, false, false, source, game);
                    }
            }
        });
        return true;
    }
}
