package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CauldronsGift extends CardImpl {

    public CauldronsGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Adamant — If at least three black mana was spent to cast this spell, put the top four cards of your library into your graveyard.
        // You may choose a creature card in your graveyard. If you do, return it to the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new CauldronsGiftEffect());
    }

    private CauldronsGift(final CauldronsGift card) {
        super(card);
    }

    @Override
    public CauldronsGift copy() {
        return new CauldronsGift(this);
    }
}

class CauldronsGiftEffect extends OneShotEffect {

    CauldronsGiftEffect() {
        super(Outcome.Benefit);
        staticText = "<i>Adamant</i> &mdash; If at least three black mana was spent to cast this spell, " +
                "mill four cards. <br>You may choose a creature card in your graveyard. " +
                "If you do, return it to the battlefield with an additional +1/+1 counter on it.";
    }

    private CauldronsGiftEffect(final CauldronsGiftEffect effect) {
        super(effect);
    }

    @Override
    public CauldronsGiftEffect copy() {
        return new CauldronsGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (AdamantCondition.BLACK.apply(game, source)) {
            player.millCards(4, source, game);
        }
        if (player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) == 0
                || !player.chooseUse(outcome, "Choose a creature card in your graveyard to return to the battlefield?", source, game)) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        target.setNotTarget(true);
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            Counters countersToAdd = new Counters();
            countersToAdd.addCounter(CounterType.P1P1.createInstance());
            game.setEnterWithCounters(card.getId(), countersToAdd);
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }

    @Override
    public Condition getCondition() {
        return AdamantCondition.BLACK;
    }
}
