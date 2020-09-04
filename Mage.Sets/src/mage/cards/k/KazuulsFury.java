package mage.cards.k;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author TheElk801
 */
public final class KazuulsFury extends CardImpl {

    public KazuulsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.k.KazuulsCliffs.class;

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Kazuul's Fury deals damage equal to the sacrificed creatures power to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(SacrificeCostCreaturesPower.instance)
                .setText("{this} deals damage equal to the sacrificed creature's power to any target"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private KazuulsFury(final KazuulsFury card) {
        super(card);
    }

    @Override
    public KazuulsFury copy() {
        return new KazuulsFury(this);
    }
}
