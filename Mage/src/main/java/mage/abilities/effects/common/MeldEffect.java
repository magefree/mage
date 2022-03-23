package mage.abilities.effects.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class MeldEffect extends OneShotEffect {

    private final String meldWithName;
    private final MeldCard meldCard;

    public MeldEffect(String meldWithName, MeldCard meldCard) {
        super(Outcome.Benefit);
        this.meldWithName = meldWithName;
        this.meldCard = meldCard;
    }

    public MeldEffect(final MeldEffect effect) {
        super(effect);
        this.meldWithName = effect.meldWithName;
        this.meldCard = effect.meldCard;
    }

    @Override
    public MeldEffect copy() {
        return new MeldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Find the two permanents to meld.
            UUID sourceId = source != null ? source.getSourceId() : null;
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature named " + meldWithName);
            filter.add(new NamePredicate(meldWithName));
            TargetPermanent target = new TargetControlledCreaturePermanent(filter);
            Set<UUID> meldWithList = target.possibleTargets(source.getControllerId(), source, game);
            if (meldWithList.isEmpty()) {
                return false; // possible permanent has left the battlefield meanwhile
            }
            UUID meldWithId = null;
            if (meldWithList.size() == 1) {
                meldWithId = meldWithList.iterator().next();
            } else {
                if (controller.choose(Outcome.BoostCreature, target, source, game)) {
                    meldWithId = target.getFirstTarget();
                }
            }
            // Exile the two permanents to meld.
            Permanent sourcePermanent = game.getPermanent(sourceId);
            Permanent meldWithPermanent = game.getPermanent(meldWithId);
            if (sourcePermanent != null && meldWithPermanent != null) {
                Set<Card> toExile = new HashSet<>();
                toExile.add(sourcePermanent);
                toExile.add(meldWithPermanent);
                controller.moveCards(toExile, Zone.EXILED, source, game);
                // Create the meld card and move it to the battlefield.
                Card sourceCard = game.getExile().getCard(sourceId, game);
                Card meldWithCard = game.getExile().getCard(meldWithId, game);
                if (sourceCard != null && !sourceCard.isCopy() && meldWithCard != null && !meldWithCard.isCopy()) {
                    meldCard.setOwnerId(controller.getId());
                    meldCard.setTopHalfCard(meldWithCard, game);
                    meldCard.setBottomHalfCard(sourceCard, game);
                    meldCard.setMelded(true, game);
                    game.addMeldCard(meldCard.getId(), meldCard);
                    game.getState().addCard(meldCard);
                    meldCard.setZone(Zone.EXILED, game);
                    controller.moveCards(meldCard, Zone.BATTLEFIELD, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
