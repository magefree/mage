
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PutOnLibraryTargetEffect extends OneShotEffect {

    boolean onTop;

    public PutOnLibraryTargetEffect(boolean onTop) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
    }

    public PutOnLibraryTargetEffect(boolean onTop, String rule) {
        super(Outcome.ReturnToHand);
        this.onTop = onTop;
        this.staticText = rule;
    }

    public PutOnLibraryTargetEffect(final PutOnLibraryTargetEffect effect) {
        super(effect);
        this.onTop = effect.onTop;
    }

    @Override
    public PutOnLibraryTargetEffect copy() {
        return new PutOnLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Card> cards = new ArrayList<>();
            List<Permanent> permanents = new ArrayList<>();
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                switch (game.getState().getZone(targetId)) {
                    case BATTLEFIELD:
                        Permanent permanent = game.getPermanent(targetId);
                        if (permanent != null) {
                            permanents.add(permanent);
                        }
                        break;
                    case GRAVEYARD:
                    case STACK:
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            cards.add(card);
                        }
                        break;
                }
            }
            // Plow Under
            // 10/4/2004 	The owner decides the order the two lands are stacked there.
            while (!cards.isEmpty()) {
                Card card = cards.iterator().next();
                if (card != null) {
                    Player owner = game.getPlayer(card.getOwnerId());
                    Cards cardsPlayer = new CardsImpl();
                    for (Iterator<Card> iterator = cards.iterator(); iterator.hasNext(); ) {
                        Card next = iterator.next();
                        if (next.isOwnedBy(owner.getId())) {
                            cardsPlayer.add(next);
                            iterator.remove();
                        }
                    }
                    if (onTop) {
                        owner.putCardsOnTopOfLibrary(cardsPlayer, game, source, true);
                    } else {
                        owner.putCardsOnBottomOfLibrary(cardsPlayer, game, source, true);
                    }
                }
            }
            while (!permanents.isEmpty()) {
                Permanent permanent = permanents.iterator().next();
                if (permanent != null) {
                    Player owner = game.getPlayer(permanent.getOwnerId());
                    Cards cardsPlayer = new CardsImpl();
                    for (Iterator<Permanent> iterator = permanents.iterator(); iterator.hasNext(); ) {
                        Permanent next = iterator.next();
                        if (next.isOwnedBy(owner.getId())) {
                            cardsPlayer.add(next);
                            iterator.remove();
                        }
                    }
                    if (onTop) {
                        owner.putCardsOnTopOfLibrary(cardsPlayer, game, source, true);
                    } else {
                        owner.putCardsOnBottomOfLibrary(cardsPlayer, game, source, true);
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public String getText(Mode mode) {
        if (this.staticText != null && !this.staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        sb.append("put ");
        if (target.getMaxNumberOfTargets() == 0 || target.getMaxNumberOfTargets() == Integer.MAX_VALUE) {
            sb.append("any number of ");
        } else if (target.getMaxNumberOfTargets() != 1 || target.getNumberOfTargets() != 1) {
            if (target.getMaxNumberOfTargets() > target.getNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
        }
        sb.append("target ").append(mode.getTargets().get(0).getTargetName()).append(" on ");
        sb.append(onTop ? "top" : "the bottom").append(" of its owner's library");

        return sb.toString();

    }
}
