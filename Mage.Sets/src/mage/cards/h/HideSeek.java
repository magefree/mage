
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class HideSeek extends SplitCard {

    public HideSeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}", "{W}{B}", SpellAbilityType.SPLIT);

        // Hide
        // Put target artifact or enchantment on the bottom of its owner's library.
        getLeftHalfCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // Seek
        // Search target opponent's library for a card and exile it. You gain life equal to its converted mana cost. Then that player shuffles their library..
        getRightHalfCard().getSpellAbility().addEffect(new SeekEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());

    }

    private HideSeek(final HideSeek card) {
        super(card);
    }

    @Override
    public HideSeek copy() {
        return new HideSeek(this);
    }
}

class SeekEffect extends OneShotEffect {

    public SeekEffect() {
        super(Outcome.GainLife);
        staticText = "Search target opponent's library for a card and exile it. You gain life equal to its mana value. Then that player shuffles";
    }

    public SeekEffect(final SeekEffect effect) {
        super(effect);
    }

    @Override
    public SeekEffect copy() {
        return new SeekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            if (opponent.getLibrary().hasCards()) {
                TargetCardInLibrary target = new TargetCardInLibrary();
                if (player.searchLibrary(target, source, game, opponent.getId())) {
                    UUID targetId = target.getFirstTarget();
                    Card card = opponent.getLibrary().remove(targetId, game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, null, null, source, game, Zone.LIBRARY, true);
                        int cmc = card.getManaValue();
                        if (cmc > 0) {
                            player.gainLife(cmc, game, source);
                        }
                    }
                }
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
