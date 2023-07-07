package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class ShadowOfTheEnemy extends CardImpl {

    public ShadowOfTheEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}");

        // Exile all creature cards from target player's graveyard.
        // You may cast spells from among those cards for as long as
        // they remain exiled, and mana of any type can be spent to cast them.
        this.getSpellAbility().addEffect(new ShadowOfTheEnemyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ShadowOfTheEnemy(final ShadowOfTheEnemy card) {
        super(card);
    }

    @Override
    public ShadowOfTheEnemy copy() {
        return new ShadowOfTheEnemy(this);
    }
}

class ShadowOfTheEnemyEffect extends OneShotEffect {

    ShadowOfTheEnemyEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from target player's graveyard. " +
            "You may cast spells from among those cards for as long as " +
            "they remain exiled, and mana of any type can be spent to cast them.";
    }

    private ShadowOfTheEnemyEffect(final ShadowOfTheEnemyEffect effect) {
        super(effect);
    }

    @Override
    public ShadowOfTheEnemyEffect copy() {
        return new ShadowOfTheEnemyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if(player == null || targetPlayer == null || sourceObject == null){
            return false;
        }

        Set<Card> cards =
            targetPlayer
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game);

        if(cards.isEmpty()){
            return true;
        }

        // exile the cards
        // allow to cast the cards
        // and you may spend mana as though it were mana of any color to cast it
        CardUtil.exileCardsAndMakeCastable(
            game, source, cards, Duration.EndOfGame,
            CardUtil.CastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR, null);

        return true;
    }
}