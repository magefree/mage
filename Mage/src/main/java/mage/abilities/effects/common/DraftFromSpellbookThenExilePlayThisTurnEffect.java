package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sidorovich77
 */
public class DraftFromSpellbookThenExilePlayThisTurnEffect extends OneShotEffect {

    private final List<String> spellbook;

    public DraftFromSpellbookThenExilePlayThisTurnEffect(List<String> spellbook) {
        super(Outcome.DrawCard);
        this.spellbook = spellbook;
        staticText = "draft a card from {this}'s spellbook, " +
                "then exile it. Until end of turn, you may play that card.";
    }

    private DraftFromSpellbookThenExilePlayThisTurnEffect(final DraftFromSpellbookThenExilePlayThisTurnEffect effect) {
        super(effect);
        this.spellbook = effect.spellbook;
    }

    @Override
    public DraftFromSpellbookThenExilePlayThisTurnEffect copy() {
        return new DraftFromSpellbookThenExilePlayThisTurnEffect(this);
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
                .findCards(new CardCriteria().name(cardName))
                .stream()
                .findFirst()
                .orElse(null);
        if (cardInfo == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        cards.add(cardInfo.createCard());
        game.loadCards(cards, player.getId());
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, cards, TargetController.YOU, Duration.EndOfTurn,
                false, false, false);
        return true;
    }
}
