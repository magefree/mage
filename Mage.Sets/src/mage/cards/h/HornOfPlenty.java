package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class HornOfPlenty extends CardImpl {

    public HornOfPlenty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Whenever a player casts a spell, they may pay {1}. If that player does, they draw a card at the beginning of the next end step.
        this.addAbility(new SpellCastAllTriggeredAbility(new HornOfPlentyEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER));
    }

    private HornOfPlenty(final HornOfPlenty card) {
        super(card);
    }

    @Override
    public HornOfPlenty copy() {
        return new HornOfPlenty(this);
    }
}

class HornOfPlentyEffect extends OneShotEffect {

    public HornOfPlentyEffect() {
        super(Outcome.Detriment);
        this.staticText = "they may pay {1}. If that player does, they draw a card at the beginning of the next end step";
    }

    public HornOfPlentyEffect(final HornOfPlentyEffect effect) {
        super(effect);
    }

    @Override
    public HornOfPlentyEffect copy() {
        return new HornOfPlentyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player caster = game.getPlayer(targetPointer.getFirst(game, source));
        if (caster != null) {
            if (caster.chooseUse(Outcome.DrawCard, "Pay {1} to draw a card at the beginning of the next end step?", source, game)) {
                Cost cost = new ManaCostsImpl<>("{1}");
                if (cost.pay(source, game, source, caster.getId(), false, null)) {
                    Effect effect = new DrawCardTargetEffect(1);
                    effect.setTargetPointer(new FixedTarget(caster.getId()));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY), source);
                    return true;
                }
            }
        }
        return false;
    }
}
