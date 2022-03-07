package mage.cards.v;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ViolentUltimatum extends CardImpl {

    public ViolentUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}{R}{R}{R}{G}{G}");


        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(3, StaticFilters.FILTER_PERMANENTS));
    }

    public ViolentUltimatum(final ViolentUltimatum card) {
        super(card);
    }

    @Override
    public ViolentUltimatum copy() {
        return new ViolentUltimatum(this);
    }

}
