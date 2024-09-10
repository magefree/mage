package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.common.TargetNonlandPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WailOfTheForgotten extends CardImpl {

    public WailOfTheForgotten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}");

        // Descend 8 -- Choose one. If there are eight or more permanent cards in your graveyard as you cast this spell, choose one or more instead.
        this.getSpellAbility().getModes().setChooseText(
                "choose one. If there are eight or more permanent cards in your graveyard as you cast this spell, choose one or more instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(DescendCondition.EIGHT);
        this.getSpellAbility().setAbilityWord(AbilityWord.DESCEND_8);

        // * Return target nonland permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // * Target opponent discards a card.
        this.getSpellAbility().addMode(new Mode(new DiscardTargetEffect(1)).addTarget(new TargetOpponent()));

        // * Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addMode(new Mode(new LookLibraryAndPickControllerEffect(
                3, 1, PutCards.HAND, PutCards.GRAVEYARD
        )));
    }

    private WailOfTheForgotten(final WailOfTheForgotten card) {
        super(card);
    }

    @Override
    public WailOfTheForgotten copy() {
        return new WailOfTheForgotten(this);
    }
}
