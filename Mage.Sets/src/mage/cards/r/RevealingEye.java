package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevealingEye extends CardImpl {

    public RevealingEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.EYE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When this creature transforms into Revealing Eye, target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card, then draws a card.
        Ability ability = new TransformIntoSourceTriggeredAbility(new RevealingEyeEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RevealingEye(final RevealingEye card) {
        super(card);
    }

    @Override
    public RevealingEye copy() {
        return new RevealingEye(this);
    }
}

class RevealingEyeEffect extends OneShotEffect {

    RevealingEyeEffect() {
        super(Outcome.Discard);
        staticText = "target opponent reveals their hand. You may choose a nonland card from it. " +
                "If you do, that player discards that card, then draws a card";
    }

    private RevealingEyeEffect(final RevealingEyeEffect effect) {
        super(effect);
    }

    @Override
    public RevealingEyeEffect copy() {
        return new RevealingEyeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        if (opponent.getHand().count(StaticFilters.FILTER_CARD_NON_LAND, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(outcome, opponent.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        opponent.discard(card, false, source, game);
        opponent.drawCards(1, source, game);
        return true;
    }
}
