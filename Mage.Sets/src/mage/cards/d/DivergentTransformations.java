
package mage.cards.d;

import java.util.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DivergentTransformations extends CardImpl {

    public DivergentTransformations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{R}");

        // Undaunted
        this.addAbility(new UndauntedAbility());
        // Exile two target creatures. For each of those creatures, its controller reveals cards from the top of their library until they reveal a creature card, puts that card onto the battlefield, then shuffles the rest into their library.
        this.getSpellAbility().addEffect(new DivergentTransformationsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));

    }

    private DivergentTransformations(final DivergentTransformations card) {
        super(card);
    }

    @Override
    public DivergentTransformations copy() {
        return new DivergentTransformations(this);
    }
}

class DivergentTransformationsEffect extends OneShotEffect {

    public DivergentTransformationsEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile two target creatures. For each of those creatures, its controller reveals cards from the top of their library "
                + "until they reveal a creature card, puts that card onto the battlefield, then shuffles the rest into their library";
    }

    public DivergentTransformationsEffect(final DivergentTransformationsEffect effect) {
        super(effect);
    }

    @Override
    public DivergentTransformationsEffect copy() {
        return new DivergentTransformationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            List<UUID> controllerList = new ArrayList<>();
            Set<Card> toExile = new HashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    toExile.add(permanent);
                    controllerList.add(permanent.getControllerId());
                }
            }
            controller.moveCards(toExile, Zone.EXILED, source, game);
            for (UUID playerId : controllerList) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        Cards toReveal = new CardsImpl();
                        for (Card card : player.getLibrary().getCards(game)) {
                            toReveal.add(card);
                            if (card.isCreature(game)) {
                                player.revealCards(source, toReveal, game);
                                player.moveCards(card, Zone.BATTLEFIELD, source, game);
                                toReveal.remove(card);
                                break;
                            }
                        }
                        if (!toReveal.isEmpty()) {
                            player.shuffleLibrary(source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
