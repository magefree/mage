
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author North
 */
public final class MemorysJourney extends CardImpl {

    public MemorysJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Target player shuffles up to three target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new MemorysJourneyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new MemorysJourneyTarget());
        // Flashback {G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{G}"), TimingRule.INSTANT));
    }

    public MemorysJourney(final MemorysJourney card) {
        super(card);
    }

    @Override
    public MemorysJourney copy() {
        return new MemorysJourney(this);
    }
}

class MemorysJourneyEffect extends OneShotEffect {

    public MemorysJourneyEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to three target cards from their graveyard into their library";
    }

    public MemorysJourneyEffect(final MemorysJourneyEffect effect) {
        super(effect);
    }

    @Override
    public MemorysJourneyEffect copy() {
        return new MemorysJourneyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.shuffleCardsToLibrary(new CardsImpl(source.getTargets().get(1).getTargets()), game, source);
        }
        return false;
    }
}

class MemorysJourneyTarget extends TargetCardInGraveyard {

    public MemorysJourneyTarget() {
        super(0, 3, new FilterCard());
    }

    public MemorysJourneyTarget(final MemorysJourneyTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public MemorysJourneyTarget copy() {
        return new MemorysJourneyTarget(this);
    }
}
