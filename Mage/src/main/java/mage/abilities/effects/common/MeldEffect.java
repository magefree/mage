package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.MeldCard;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;

/**
 * @author emerald000
 */
public class MeldEffect extends OneShotEffect {

    private final String meldWithName;
    private final String meldIntoName;

    public MeldEffect(String meldWithName, String meldIntoName) {
        super(Outcome.Benefit);
        this.meldWithName = meldWithName;
        this.meldIntoName = meldIntoName;
    }

    public MeldEffect(final MeldEffect effect) {
        super(effect);
        this.meldWithName = effect.meldWithName;
        this.meldIntoName = effect.meldIntoName;
    }

    @Override
    public MeldEffect copy() {
        return new MeldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null
                || sourcePermanent == null
                || !sourcePermanent.isControlledBy(controller.getId())
                || !sourcePermanent.isOwnedBy(controller.getId())) {
            return false;
        }
        // Find the two permanents to meld.
        FilterPermanent filter = new FilterControlledPermanent("permanent named " + meldWithName);
        filter.add(new NamePredicate(meldWithName));
        filter.add(TargetController.YOU.getOwnerPredicate());
        if (!game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        controller.choose(outcome, target, source, game);

        Permanent meldWithPermanent = game.getPermanent(target.getFirstTarget());
        if (sourcePermanent == null || meldWithPermanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(sourcePermanent);
        cards.add(meldWithPermanent);
        controller.moveCards(cards, Zone.EXILED, source, game);
        // Create the meld card and move it to the battlefield.
        Card sourceCard = cards.get(sourcePermanent.getId(), game);
        Card meldWithCard = cards.get(meldWithPermanent.getId(), game);
        if (sourceCard == null
                || meldWithCard == null
                || !sourceCard.meldsWith(meldWithCard)
                || !meldWithCard.meldsWith(sourceCard)) {
            return true;
        }
        List<CardInfo> cardInfoList = CardRepository.instance.findCards(
                new CardCriteria()
                        .name(meldIntoName)
                        .setCodes(sourceCard.getExpansionSetCode())
                        .nightCard(true)
        );
        if (cardInfoList.isEmpty()) {
            return false;
        }
        MeldCard meldCard = (MeldCard) cardInfoList.get(0).getCard().copy();
        meldCard.setOwnerId(controller.getId());
        meldCard.setTopHalfCard(meldWithCard, game);
        meldCard.setBottomHalfCard(sourceCard, game);
        meldCard.setMelded(true, game);
        game.addMeldCard(meldCard.getId(), meldCard);
        game.getState().addCard(meldCard);
        meldCard.setZone(Zone.EXILED, game);
        controller.moveCards(meldCard, Zone.BATTLEFIELD, source, game);
        return true;
    }
}
