package mage.cards.s;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;

/**
 * @author jmharmon
 */

public final class SoulStrings extends CardImpl {

    public SoulStrings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Return two target creature cards from your graveyard to your hand unless any player pays {X}.
        Effect effect = new DoUnlessAnyPlayerPaysEffect(
                new ReturnFromGraveyardToHandTargetEffect(), ManacostVariableValue.REGULAR);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
    }

    private SoulStrings(final SoulStrings card) {
        super(card);
    }

    @Override
    public SoulStrings copy() {
        return new SoulStrings(this);
    }
}
