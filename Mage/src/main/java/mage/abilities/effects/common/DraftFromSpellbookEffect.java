package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author TheElk801
 */
public class DraftFromSpellbookEffect extends OneShotEffect {

    private final List<String> spellbook;

    public DraftFromSpellbookEffect(List<String> spellbook) {
        super(Outcome.DrawCard);
        this.spellbook = spellbook;
        staticText = "draft a card from {this}'s spellbook";
    }

    private DraftFromSpellbookEffect(final DraftFromSpellbookEffect effect) {
        super(effect);
        this.spellbook = effect.spellbook;
    }

    @Override
    public DraftFromSpellbookEffect copy() {
        return new DraftFromSpellbookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<String> toSelect = new HashSet<>();
        while (toSelect.size() < 3) {
            toSelect.add(RandomUtil.randomFromCollection(spellbook));
        }
        Choice choice = new ChoiceImpl(true, ChoiceHintType.CARD);
        choice.setMessage("Choose a card to draft");
        choice.setChoices(toSelect);
        player.choose(outcome, choice, game);
        String cardName = choice.getChoice();
        if (cardName == null) {
            return false;
        }
        CardInfo cardInfo = CardRepository
                .instance
                .findCards(new CardCriteria().nameExact(cardName))
                .stream()
                .findFirst()
                .orElse(null);
        if (cardInfo == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        cards.add(cardInfo.getCard());
        game.loadCards(cards, player.getId());
        player.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
