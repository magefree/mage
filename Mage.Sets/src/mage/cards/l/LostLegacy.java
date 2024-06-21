
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

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
        super(true, "target player's", "any number of cards with that name", true);
    }

    private LostLegacyEffect(final LostLegacyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && cardName != null && !cardName.isEmpty()) {
            return applySearchAndExile(game, source, cardName, getTargetPointer().getFirst(game, source));
        }
        return false;
    }

    @Override
    public LostLegacyEffect copy() {
        return new LostLegacyEffect(this);
    }
}
