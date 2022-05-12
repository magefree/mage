package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlayerOrPlaneswalker;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class HansEriksson extends CardImpl {

    public HansEriksson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Hans Eriksson attacks, reveal the top card of your library. If it's a creature card, put it onto the battlefield tapped and attacking defending player or a planeswalker they control. Otherwise, put that card into your hand. When you put a creature card onto the battlefield this way, it fights Hans Eriksson.
        this.addAbility(new AttacksTriggeredAbility(
                new HansErikssonEffect(), false, "", SetTargetPointer.PLAYER
        ));
    }

    private HansEriksson(final HansEriksson card) {
        super(card);
    }

    @Override
    public HansEriksson copy() {
        return new HansEriksson(this);
    }
}

class HansErikssonEffect extends OneShotEffect {

    HansErikssonEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top card of your library. If it's a creature card, " +
                "put it onto the battlefield tapped and attacking defending player " +
                "or a planeswalker they control. Otherwise, put that card into your hand. " +
                "When you put a creature card onto the battlefield this way, it fights {this}";
    }

    private HansErikssonEffect(final HansErikssonEffect effect) {
        super(effect);
    }

    @Override
    public HansErikssonEffect copy() {
        return new HansErikssonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isCreature(game)) {
            return player.moveCards(card, Zone.HAND, source, game);
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return true;
        }
        UUID defendingPlayerId = getTargetPointer().getFirst(game, source);
        UUID defenderId;
        if (game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                defendingPlayerId, source, game
        ) < 1) {
            defenderId = defendingPlayerId;
        } else {
            FilterPlayerOrPlaneswalker filter = new FilterPlayerOrPlaneswalker(
                    "defending player or a planeswalker they control"
            );
            filter.getPlayerFilter().add(new PlayerIdPredicate(defendingPlayerId));
            filter.getPermanentFilter().add(new ControllerIdPredicate(defendingPlayerId));
            TargetPlayerOrPlaneswalker target = new TargetPlayerOrPlaneswalker(filter);
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            defenderId = target.getFirstTarget();
        }
        if (defenderId != null) {
            game.getCombat().addAttackerToCombat(permanent.getId(), defenderId, game);
        }
        Effect fightEffect = new FightTargetSourceEffect();
        fightEffect.setTargetPointer(new FixedTarget(permanent, game));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                fightEffect, false,
                "When you put a creature card onto the battlefield this way, it fights {this}"
        );        
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
// "Ach! Hans, run! It’s the lhurgoyf!"
// —Saffi Eriksdotter, last words
