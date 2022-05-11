
package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.events.TargetEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public final class DrafnasRestoration extends CardImpl {

    public DrafnasRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Put any number of target artifact cards from target player's graveyard on top of their library in any order.
        this.getSpellAbility().addEffect(new DrafnasRestorationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new DrafnasRestorationTarget());
    }

    private DrafnasRestoration(final DrafnasRestoration card) {
        super(card);
    }

    @Override
    public DrafnasRestoration copy() {
        return new DrafnasRestoration(this);
    }
}

class DrafnasRestorationTarget extends TargetCardInGraveyard {

    DrafnasRestorationTarget() {
        super(0, Integer.MAX_VALUE, new FilterArtifactCard("any number of artifact cards from that player's graveyard"));
    }

    DrafnasRestorationTarget(final DrafnasRestorationTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        return targetPlayer != null && targetPlayer.getGraveyard().contains(id) && super.canTarget(id, source, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);
        if (object instanceof StackObject) {
            Player targetPlayer = game.getPlayer(((StackObject) object).getStackAbility().getFirstTarget());
            if (targetPlayer != null) {
                for (Card card : targetPlayer.getGraveyard().getCards(filter, sourceControllerId, source, game)) {
                    if (source == null || source.getSourceId() == null || isNotTarget() || !game.replaceEvent(new TargetEvent(card, source.getSourceId(), sourceControllerId))) {
                        possibleTargets.add(card.getId());
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public DrafnasRestorationTarget copy() {
        return new DrafnasRestorationTarget(this);
    }
}

class DrafnasRestorationEffect extends OneShotEffect {

    DrafnasRestorationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put any number of target artifact cards from target player's graveyard on top of their library in any order.";
    }

    DrafnasRestorationEffect(final DrafnasRestorationEffect effect) {
        super(effect);
    }

    @Override
    public DrafnasRestorationEffect copy() {
        return new DrafnasRestorationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(source.getTargets().get(1).getTargets());
            controller.putCardsOnTopOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
