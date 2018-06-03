
package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public final class IndomitableCreativity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }

    public IndomitableCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Destroy X target artifacts and/or creatures. For each permanent destroyed this way, its controller reveals cards from the top of their library until an artifact or creature card is revealed and exiles that card. Those players put the exiled card onto the battlefield, then shuffle their libraries.
        getSpellAbility().addEffect(new IndomitableCreativityEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public IndomitableCreativity(final IndomitableCreativity card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetPermanent(xValue, xValue, filter, false));
        }
    }

    @Override
    public IndomitableCreativity copy() {
        return new IndomitableCreativity(this);
    }
}

class IndomitableCreativityEffect extends OneShotEffect {

    public IndomitableCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy X target artifacts and/or creatures. For each permanent destroyed this way, its controller reveals cards from the top of their library until an artifact or creature card is revealed and exiles that card. Those players put the exiled card onto the battlefield, then shuffle their libraries";
    }

    public IndomitableCreativityEffect(final IndomitableCreativityEffect effect) {
        super(effect);
    }

    @Override
    public IndomitableCreativityEffect copy() {
        return new IndomitableCreativityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> destroyedPermanents = new ArrayList<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent target = game.getPermanent(targetId);
                if (target != null) {
                    if (target.destroy(source.getSourceId(), game, false)) {
                        destroyedPermanents.add(target);
                    }
                }
            }
            for (Permanent permanent : destroyedPermanents) {
                Player controllerOfDestroyedCreature = game.getPlayer(permanent.getControllerId());
                if (controllerOfDestroyedCreature != null) {
                    Library library = controllerOfDestroyedCreature.getLibrary();
                    if (library.hasCards()) {
                        Cards cardsToReaveal = new CardsImpl();
                        for (Card card : library.getCards(game)) {
                            cardsToReaveal.add(card);
                            if (card.isCreature() || card.isArtifact()) {
                                controllerOfDestroyedCreature.moveCards(card, Zone.EXILED, source, game);
                                controllerOfDestroyedCreature.moveCards(card, Zone.BATTLEFIELD, source, game);
                                break;
                            }
                        }
                        controllerOfDestroyedCreature.revealCards(source, " for destroyed " + permanent.getIdName(), cardsToReaveal, game);
                        controllerOfDestroyedCreature.shuffleLibrary(source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
