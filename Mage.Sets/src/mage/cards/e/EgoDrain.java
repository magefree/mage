package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EgoDrain extends CardImpl {

    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(new FilterPermanent(SubType.FAERIE, ""), true);
    private static final Hint hint = new ConditionHint(condition, "Faerie controlled");

    public EgoDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target player reveals their hand. You choose a nonland card from it. That player discards that card. Then exile a card from your hand if you don't control a Faerie.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new EgoDrainEffect(),
                new InvertCondition(condition),
                "then exile a card from your hand if you don't control a Faerie."
        ));
        this.getSpellAbility().addHint(hint);

    }

    private EgoDrain(final EgoDrain card) {
        super(card);
    }

    @Override
    public EgoDrain copy() {
        return new EgoDrain(this);
    }
}

class EgoDrainEffect extends OneShotEffect {

    EgoDrainEffect() {
        super(Outcome.Exile);
    }

    public EgoDrainEffect(final EgoDrainEffect effect) {
        super(effect);
    }

    @Override
    public EgoDrainEffect copy() {
        return new EgoDrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(new FilterCard("a card from your hand to exile"));
        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }

        controller.choose(outcome, target, source, game);
        Card cardToExile = game.getCard(target.getFirstTarget());
        if (cardToExile != null) {
            return cardToExile.moveToExile(null, "", source, game);
        }

        return false;
    }
}
