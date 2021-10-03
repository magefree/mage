
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class TrackersInstincts extends CardImpl {

    public TrackersInstincts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new TrackersInstinctsEffect());
        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{U}")));
    }

    private TrackersInstincts(final TrackersInstincts card) {
        super(card);
    }

    @Override
    public TrackersInstincts copy() {
        return new TrackersInstincts(this);
    }
}

class TrackersInstinctsEffect extends OneShotEffect {

    public TrackersInstinctsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard";
    }

    public TrackersInstinctsEffect(final TrackersInstinctsEffect effect) {
        super(effect);
    }

    @Override
    public TrackersInstinctsEffect copy() {
        return new TrackersInstinctsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
            boolean creaturesFound = cards.count(StaticFilters.FILTER_CARD_CREATURE, game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(source, cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to put in hand"));
                if (creaturesFound && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cards.remove(card);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
