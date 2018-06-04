
package mage.cards.c;

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
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CranialExtraction extends CardImpl {

    public CranialExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.subtype.add(SubType.ARCANE);

        /* Name a nonland card. Search target player's graveyard, hand, and library for
        * all cards with that name and exile them. Then that player shuffles their library. */
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CranialExtractionEffect());
    }

    public CranialExtraction(final CranialExtraction card) {
        super(card);
    }

    @Override
    public CranialExtraction copy() {
        return new CranialExtraction(this);
    }

}

class CranialExtractionEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    CranialExtractionEffect() {
        super(false, "target player's", "all cards with that name");
    }

    CranialExtractionEffect(final CranialExtractionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            cardChoice.setMessage("Name a nonland card");

            if (!controller.choose(Outcome.Exile, cardChoice, game)) {
                return false;
            }
            String cardName = cardChoice.getChoice();
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (sourceObject != null) {
                game.informPlayers(sourceObject.getName() + " named card: [" + cardName + ']');
            }
            super.applySearchAndExile(game, source, cardName, player.getId());
        }
        return true;
    }

    @Override
    public CranialExtractionEffect copy() {
        return new CranialExtractionEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Name a nonland card. " + super.getText(mode);
    }
}
