
package mage.cards.r;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class Repopulate extends CardImpl {

    public Repopulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Shuffle all creature cards from target player's graveyard into that player's library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new RepopulateEffect());
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public Repopulate(final Repopulate card) {
        super(card);
    }

    @Override
    public Repopulate copy() {
        return new Repopulate(this);
    }
}

class RepopulateEffect extends OneShotEffect {

    RepopulateEffect() {
        super(Outcome.Benefit);
        staticText = "Shuffle all creature cards from target player's graveyard into that player's library";
    }

    RepopulateEffect(final RepopulateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Set<Card> cards = player.getGraveyard().getCards(new FilterCreatureCard(), game);
            for(Card card : cards)
            {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public RepopulateEffect copy() {
        return new RepopulateEffect(this);
    }
}
