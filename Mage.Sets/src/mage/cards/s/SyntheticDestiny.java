
package mage.cards.s;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SyntheticDestiny extends CardImpl {

    public SyntheticDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Exile all creatures you control. At the beginning of the next end step, reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library.
        getSpellAbility().addEffect(new SyntheticDestinyEffect());
    }

    private SyntheticDestiny(final SyntheticDestiny card) {
        super(card);
    }

    @Override
    public SyntheticDestiny copy() {
        return new SyntheticDestiny(this);
    }
}

class SyntheticDestinyEffect extends OneShotEffect {

    public SyntheticDestinyEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile all creatures you control. At the beginning of the next end step, reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    private SyntheticDestinyEffect(final SyntheticDestinyEffect effect) {
        super(effect);
    }

    @Override
    public SyntheticDestinyEffect copy() {
        return new SyntheticDestinyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = new HashSet<>();
            cardsToExile.addAll(game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game));
            controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            //Delayed ability
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                    new SyntheticDestinyDelayedEffect(cardsToExile.size())), source);

            return true;
        }
        return false;
    }
}

class SyntheticDestinyDelayedEffect extends OneShotEffect {

    protected int numberOfCards;

    public SyntheticDestinyDelayedEffect(int numberOfCards) {
        super(Outcome.PutCreatureInPlay);
        this.numberOfCards = numberOfCards;
        this.staticText = "reveal cards from the top of your library until you reveal that many creature cards, put all creature cards revealed this way onto the battlefield, then shuffle the rest of the revealed cards into your library";
    }

    private SyntheticDestinyDelayedEffect(final SyntheticDestinyDelayedEffect effect) {
        super(effect);
        this.numberOfCards = effect.numberOfCards;
    }

    @Override
    public SyntheticDestinyDelayedEffect copy() {
        return new SyntheticDestinyDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards revealed = new CardsImpl();
            Set<Card> creatureCards = new LinkedHashSet<>();
            for (Card card : controller.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card.isCreature(game)) {
                    creatureCards.add(card);
                }
                if (creatureCards.size() >= numberOfCards) {
                    break;
                }
            }
            controller.revealCards(source, revealed, game);
            controller.moveCards(creatureCards, Zone.BATTLEFIELD, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
