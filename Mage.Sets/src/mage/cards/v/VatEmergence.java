package mage.cards.v;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VatEmergence extends CardImpl {

    public VatEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Put target creature card from a graveyard onto the battlefield under your control. Proliferate.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private VatEmergence(final VatEmergence card) {
        super(card);
    }

    @Override
    public VatEmergence copy() {
        return new VatEmergence(this);
    }
}
