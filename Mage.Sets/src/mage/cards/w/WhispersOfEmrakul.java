package mage.cards.w;

import java.util.UUID;

import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 * @author fireshoes
 */
public final class WhispersOfEmrakul extends CardImpl {

    public WhispersOfEmrakul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent discards a card at random.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardTargetEffect(1, true),
                new InvertCondition(DeliriumCondition.instance),
                "Target opponent discards a card at random."));

        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, that player discards two cards at random instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardTargetEffect(2, true),
                DeliriumCondition.instance,
                "<br><i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, that player discards two cards at random instead"));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addHint(CardTypesInGraveyardHint.YOU);
    }

    private WhispersOfEmrakul(final WhispersOfEmrakul card) {
        super(card);
    }

    @Override
    public WhispersOfEmrakul copy() {
        return new WhispersOfEmrakul(this);
    }
}
