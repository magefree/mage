package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class ReturnThePast extends CardImpl {

    public ReturnThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");


        // As long as it's your turn, each instant and sorcery card in your graveyard has flashback. Its flashback cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new ReturnThePastEffect()).addHint(MyTurnHint.instance));
    }

    private ReturnThePast(final ReturnThePast card) {
        super(card);
    }

    @Override
    public ReturnThePast copy() {
        return new ReturnThePast(this);
    }
}

//Based on LierDiscipleOfTheDrownedFlashbackEffect
class ReturnThePastEffect extends ContinuousEffectImpl {

    ReturnThePastEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "As long as it's your turn, each instant and sorcery card in your graveyard has flashback. " +
                "Its flashback cost is equal to that card's mana cost";
    }

    private ReturnThePastEffect(final ReturnThePastEffect effect) {
        super(effect);
    }

    @Override
    public ReturnThePastEffect copy() {
        return new ReturnThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getActivePlayerId() != source.getControllerId()) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game)) {
            Ability ability = new FlashbackAbility(card, card.getManaCost());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}
