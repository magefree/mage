
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class LostLegacy extends CardImpl {

    public LostLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Name a nonartifact, nonland card. Search target player's graveyard, hand and library for any number of cards with that name and exile them. That player shuffles their library, then draws a card for each card exiled from hand this way.
        this.getSpellAbility().addEffect((new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_ARTIFACT_AND_NON_LAND_NAME)));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new LostLegacyEffect());
    }

    private LostLegacy(final LostLegacy card) {
        super(card);
    }

    @Override
    public LostLegacy copy() {
        return new LostLegacy(this);
    }
}

class LostLegacyEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    LostLegacyEffect() {
        super(true, "target player's", "any number of cards with that name");
        this.staticText = "Search target player's graveyard, hand, and library for any number of cards with that name and exile them. That player shuffles, then draws a card for each card exiled from their hand this way";
    }

    LostLegacyEffect(final LostLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && cardName != null && !cardName.isEmpty()) {
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardName));
            int cardsInHandBefore = targetPlayer.getHand().count(filter, game);
            boolean result = super.applySearchAndExile(game, source, cardName, targetPointer.getFirst(game, source));
            int cardsExiled = cardsInHandBefore - targetPlayer.getHand().count(filter, game);
            if (cardsExiled > 0) {
                targetPlayer.drawCards(cardsExiled, source, game);
            }
            return result;
        }
        return false;
    }

    @Override
    public LostLegacyEffect copy() {
        return new LostLegacyEffect(this);
    }
}
