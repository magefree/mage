package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class SinsOfTheFatherStarWars extends CardImpl {

    public SinsOfTheFatherStarWars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");


        // Exile target player's graveyard. That player loses 1 life for each instant or sorcery card exiled this way.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new TheSinsOfTheFatherEffect());
    }

    private SinsOfTheFatherStarWars(final SinsOfTheFatherStarWars card) {
        super(card);
    }

    @Override
    public SinsOfTheFatherStarWars copy() {
        return new SinsOfTheFatherStarWars(this);
    }
}

class TheSinsOfTheFatherEffect extends OneShotEffect {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard();

    public TheSinsOfTheFatherEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target player's graveyard. That player loses 1 life for each instant or sorcery card exiled this way";
    }

    private TheSinsOfTheFatherEffect(final TheSinsOfTheFatherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(you != null && targetPlayer != null) {
            int exiledCards = 0;
            for(Card card : targetPlayer.getGraveyard().getCards(game)) {
                if(filter.match(card, game)) {
                    if(card.moveToExile(null, "", source, game)) {
                        exiledCards++;
                    }
                }
            }
            targetPlayer.loseLife(exiledCards, game, source, false);
            return true;
        }
        return false;
    }

    @Override
    public TheSinsOfTheFatherEffect copy() {
        return new TheSinsOfTheFatherEffect(this);
    }
}
