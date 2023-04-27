package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InfiniteObliteration extends CardImpl {

    public InfiniteObliteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose a creature card. Search target opponent's graveyard, hand, and library
        // for any number of cards with that name and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new InfiniteObliterationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private InfiniteObliteration(final InfiniteObliteration card) {
        super(card);
    }

    @Override
    public InfiniteObliteration copy() {
        return new InfiniteObliteration(this);
    }
}

class InfiniteObliterationEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public InfiniteObliterationEffect() {
        super(true, "target opponent's", "any number of cards with that name");
        this.staticText = "Choose a creature card name. " + CardUtil.getTextWithFirstCharUpperCase(this.staticText);
    }

    public InfiniteObliterationEffect(final InfiniteObliterationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return true;
        }
        String cardName = ChooseACardNameEffect.TypeOfName.CREATURE_NAME.getChoice(controller, game, source, false);
        super.applySearchAndExile(game, source, cardName, player.getId());
        return true;
    }

    @Override
    public InfiniteObliterationEffect copy() {
        return new InfiniteObliterationEffect(this);
    }
}
