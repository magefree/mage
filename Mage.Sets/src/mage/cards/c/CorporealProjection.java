package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorporealProjection extends CardImpl {

    public CorporealProjection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{R}");

        // Target creature you control gains myriad until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new MyriadAbility(false)));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {3}{U}{U}{R}{R}
        this.addAbility(new OverloadAbility(
                this,
                new GainAbilityAllEffect(
                        new MyriadAbility(false), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), new ManaCostsImpl<>("{3}{U}{U}{R}{R}")
        ));
    }

    private CorporealProjection(final CorporealProjection card) {
        super(card);
    }

    @Override
    public CorporealProjection copy() {
        return new CorporealProjection(this);
    }
}
