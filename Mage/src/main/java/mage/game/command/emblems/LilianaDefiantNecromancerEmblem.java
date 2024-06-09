package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.target.targetpointer.FixedTarget;

/**
 * @author spjspj
 */
public final class LilianaDefiantNecromancerEmblem extends Emblem {
    // You get an emblem with "Whenever a creature you control dies, return it to the battlefield under your control at the beginning of the next end step."

    public LilianaDefiantNecromancerEmblem() {
        super("Emblem Liliana");
        Ability ability = new DiesCreatureTriggeredAbility(Zone.COMMAND, new LilianaDefiantNecromancerEmblemEffect(), false, StaticFilters.FILTER_PERMANENT_A_CREATURE, true);
        this.getAbilities().add(ability);
    }

    private LilianaDefiantNecromancerEmblem(final LilianaDefiantNecromancerEmblem card) {
        super(card);
    }

    @Override
    public LilianaDefiantNecromancerEmblem copy() {
        return new LilianaDefiantNecromancerEmblem(this);
    }
}

class LilianaDefiantNecromancerEmblemEffect extends OneShotEffect {

    LilianaDefiantNecromancerEmblemEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield under your control at the beginning of the next end step";
    }

    LilianaDefiantNecromancerEmblemEffect(final LilianaDefiantNecromancerEmblemEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDefiantNecromancerEmblemEffect copy() {
        return new LilianaDefiantNecromancerEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setText("return that card to the battlefield at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY), source);
            return true;
        }
        return false;
    }
}
