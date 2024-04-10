package mage.cards.e;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntreatTheDead extends CardImpl {

    public EntreatTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{B}{B}{B}");

        // Return X target creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return X target creature cards from your graveyard to the battlefield"));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));

        // Miracle {X}{B}{B}
        this.addAbility(new MiracleAbility("{X}{B}{B}"));
    }

    private EntreatTheDead(final EntreatTheDead card) {
        super(card);
    }

    @Override
    public EntreatTheDead copy() {
        return new EntreatTheDead(this);
    }
}
