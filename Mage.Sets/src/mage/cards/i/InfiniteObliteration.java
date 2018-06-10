
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class InfiniteObliteration extends CardImpl {

    public InfiniteObliteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Name a creature card.  Search target opponent's graveyard, hand, and library
        // for any number of cards with that name and exile them.  Then that player shuffles their library.
        this.getSpellAbility().addEffect(new InfiniteObliterationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public InfiniteObliteration(final InfiniteObliteration card) {
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
    }

    public InfiniteObliterationEffect(final InfiniteObliterationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getCreatureNames());
            cardChoice.clearChoice();
            cardChoice.setMessage("Name a creature card");

            if (!controller.choose(Outcome.Exile, cardChoice, game)) {
                return false;
            }
            String cardName;
            cardName = cardChoice.getChoice();
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (sourceObject != null) {
                game.informPlayers(sourceObject.getName() + " named card: [" + cardName + ']');
            }

            super.applySearchAndExile(game, source, cardName, player.getId());
        }
        return true;
    }

    @Override
    public InfiniteObliterationEffect copy() {
        return new InfiniteObliterationEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Name a creature card. " + super.getText(mode);
    }

}
