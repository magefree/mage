package mage.cards.c;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConductElectricity extends CardImpl {

    public ConductElectricity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Conduct Electricity deals 6 damage to target creature and 2 damage to up to one target creature token.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(6, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CREATURE_TOKEN
        ).setTargetTag(2));
    }

    private ConductElectricity(final ConductElectricity card) {
        super(card);
    }

    @Override
    public ConductElectricity copy() {
        return new ConductElectricity(this);
    }
}
