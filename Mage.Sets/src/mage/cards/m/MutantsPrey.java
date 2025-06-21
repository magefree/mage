package mage.cards.m;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MutantsPrey extends CardImpl {

    public MutantsPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature you control with a +1/+1 counter on it fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private MutantsPrey(final MutantsPrey card) {
        super(card);
    }

    @Override
    public MutantsPrey copy() {
        return new MutantsPrey(this);
    }

}
