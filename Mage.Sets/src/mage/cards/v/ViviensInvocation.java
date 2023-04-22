package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensInvocation extends CardImpl {

    public ViviensInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}");

        // Look at the top seven cards of your library. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order. When a creature is put onto the battlefield this way, it deals damage equals to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new ViviensInvocationEffect());
    }

    private ViviensInvocation(final ViviensInvocation card) {
        super(card);
    }

    @Override
    public ViviensInvocation copy() {
        return new ViviensInvocation(this);
    }
}

class ViviensInvocationEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("creature card to put on the battlefield");

    ViviensInvocationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top seven cards of your library. "
                + "You may put a creature card from among them onto the battlefield. "
                + "Put the rest on the bottom of your library in a random order. "
                + "When a creature is put onto the battlefield this way, "
                + "it deals damage equal to its power to target creature an opponent controls";
    }

    private ViviensInvocationEffect(final ViviensInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard target = new TargetCard(Zone.LIBRARY, filter);
        target.setNotTarget(true);
        controller.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card == null) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            cards.remove(card);
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ViviensInvocationDamageEffect(new MageObjectReference(permanent, game)), false,
                "it deals damage equals to its power to target creature an opponent controls"
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }

    @Override
    public ViviensInvocationEffect copy() {
        return new ViviensInvocationEffect(this);
    }
}

class ViviensInvocationDamageEffect extends OneShotEffect {

    private final MageObjectReference mor;

    ViviensInvocationDamageEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private ViviensInvocationDamageEffect(final ViviensInvocationDamageEffect effect) {
        super(effect);
        mor = effect.mor;
    }

    @Override
    public ViviensInvocationDamageEffect copy() {
        return new ViviensInvocationDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (mor == null) {
            return false;
        }
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Permanent permanent = mor.getPermanent(game);
        if (creature == null || permanent == null) {
            return false;
        }
        return creature.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true) > 0;
    }
}
