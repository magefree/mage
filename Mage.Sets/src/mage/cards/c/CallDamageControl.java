package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author muz
 */
public final class CallDamageControl extends CardImpl {

    public CallDamageControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose up to two. Return those cards from your graveyard to your hand.
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(2);
        this.getSpellAbility().getModes().setChooseText(
            "Choose up to two. Return those cards from your graveyard to your hand."
        );

        // * Target artifact card.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect().setText("Target artifact card"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT)
            .withChooseHint("Target artifact card"));

        // * Target creature card.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect().setText("Target creature card"))
            .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE)
            .withChooseHint("Target creature card")
        ));

        // * Target enchantment card.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect().setText("Target enchantment card"))
            .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ENCHANTMENT)
            .withChooseHint("Target enchantment card")
        ));

        // * Target land card.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect().setText("Target land card"))
            .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND)
            .withChooseHint("Target land card")
        ));
    }

    private CallDamageControl(final CallDamageControl card) {
        super(card);
    }

    @Override
    public CallDamageControl copy() {
        return new CallDamageControl(this);
    }
}
