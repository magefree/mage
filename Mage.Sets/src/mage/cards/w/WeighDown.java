package mage.cards.w;

import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeighDown extends CardImpl {

    public WeighDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)
        ));

        // Target creature gets -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WeighDown(final WeighDown card) {
        super(card);
    }

    @Override
    public WeighDown copy() {
        return new WeighDown(this);
    }
}
