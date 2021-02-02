
package mage.cards.g;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 * @author noxx
 */
public final class GhostlyFlicker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, creatures, and/or lands you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.ARTIFACT.getPredicate()));
    }

    public GhostlyFlicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetControlledPermanent(2, 2, filter, false));
        this.getSpellAbility().addEffect(new GhostlyFlickerEffect());
    }

    private GhostlyFlicker(final GhostlyFlicker card) {
        super(card);
    }

    @Override
    public GhostlyFlicker copy() {
        return new GhostlyFlicker(this);
    }
}

class GhostlyFlickerEffect extends OneShotEffect {

    public GhostlyFlickerEffect() {
        super(Outcome.Benefit);
        staticText = "Exile two target artifacts, creatures, and/or lands you control, then return those cards to the battlefield under your control";
    }

    public GhostlyFlickerEffect(final GhostlyFlickerEffect effect) {
        super(effect);
    }

    @Override
    public GhostlyFlickerEffect copy() {
        return new GhostlyFlickerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Set<Card> toExile = new HashSet<>();
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent target = game.getPermanent(permanentId);
                if (target != null) {
                    toExile.add(target);
                }
            }
            controller.moveCards(toExile, Zone.EXILED, source, game);
            game.getState().processAction(game);
            Set<Card> toBattlefield = new HashSet<>();
            for (Card card : toExile) {
                Zone currentZone = game.getState().getZone(card.getId());
                if (Zone.BATTLEFIELD != currentZone && currentZone.isPublicZone()) {
                    toBattlefield.add(card);
                }
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}
