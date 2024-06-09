
package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IndomitableCreativity extends CardImpl {


    public IndomitableCreativity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Destroy X target artifacts and/or creatures. For each permanent destroyed this way, its controller reveals cards from the top of their library until an artifact or creature card is revealed and exiles that card. Those players put the exiled card onto the battlefield, then shuffle their libraries.
        this.getSpellAbility().addEffect(new IndomitableCreativityEffect());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private IndomitableCreativity(final IndomitableCreativity card) {
        super(card);
    }

    @Override
    public IndomitableCreativity copy() {
        return new IndomitableCreativity(this);
    }
}

class IndomitableCreativityEffect extends OneShotEffect {

    IndomitableCreativityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy X target artifacts and/or creatures. " +
                "For each permanent destroyed this way, " +
                "its controller reveals cards from the top of their library" +
                " until an artifact or creature card is revealed and exiles that card. " +
                "Those players put the exiled cards onto the battlefield, then shuffle";
    }

    private IndomitableCreativityEffect(final IndomitableCreativityEffect effect) {
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
                    if (target.destroy(source, game, false)) {
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
                            if (card.isCreature(game) || card.isArtifact(game)) {
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
