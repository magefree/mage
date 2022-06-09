
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author anonymous
 */
public final class PsychoticEpisode extends CardImpl {

    public PsychoticEpisode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Target player reveals their hand and the top card of their library. You choose a card revealed this way. That player puts the chosen card on the bottom of their library.
        this.getSpellAbility().addEffect(new PsychoticEpisodeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Madness {1}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private PsychoticEpisode(final PsychoticEpisode card) {
        super(card);
    }

    @Override
    public PsychoticEpisode copy() {
        return new PsychoticEpisode(this);
    }
}


class PsychoticEpisodeEffect extends OneShotEffect {

    PsychoticEpisodeEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals their hand and the top card of their library. You choose a card revealed this way. That player puts the chosen card on the bottom of their library.";
    }

    PsychoticEpisodeEffect(final PsychoticEpisodeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && controller != null && sourceObject != null) {
            TargetCard targetCard = new TargetCard(Zone.ALL, new FilterCard());
            targetCard.setRequired(true);
            Cards options = player.getHand().copy();
            Card topdeck = player.getLibrary().getFromTop(game);
            options.add(topdeck);
            controller.lookAtCards("Top of Library (Psychotic Episode)", topdeck, game);
            if (controller.choose(Outcome.Discard, options, targetCard, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    player.revealCards(sourceObject.getIdName(), cards, game);
                    player.putCardsOnBottomOfLibrary(cards, game, source, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PsychoticEpisodeEffect copy() {
        return new PsychoticEpisodeEffect(this);
    }
}
