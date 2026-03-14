package mage.cards.u;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnclesMusings extends CardImpl {

    public UnclesMusings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Converge -- Return up to X permanent cards from your graveyard to your hand, where X is the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect()
                .setText("return up to X permanent cards from your graveyard to your hand, " +
                        "where X is the number of colors of mana spent to cast this spell"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_PERMANENT));
        this.getSpellAbility().setTargetAdjuster(new TargetsCountAdjuster(ColorsOfManaSpentToCastCount.getInstance()));
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);

        // Exile Uncle's Musings.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private UnclesMusings(final UnclesMusings card) {
        super(card);
    }

    @Override
    public UnclesMusings copy() {
        return new UnclesMusings(this);
    }
}
