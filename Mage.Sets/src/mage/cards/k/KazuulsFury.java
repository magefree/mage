package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author JayDi85
 */
public final class KazuulsFury extends ModalDoubleFacedCard {

    public KazuulsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{R}",
                "Kazuul's Cliffs", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Kazuul's Fury
        // Instant

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getLeftHalfCard().getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Kazuul's Fury deals damage equal to the sacrificed creatures power to any target.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(SacrificeCostCreaturesPower.instance)
                .setText("{this} deals damage equal to the sacrificed creature's power to any target"));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        // 2.
        // Kazuul's Cliffs
        // Land

        // Kazuul's Cliffs enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private KazuulsFury(final KazuulsFury card) {
        super(card);
    }

    @Override
    public KazuulsFury copy() {
        return new KazuulsFury(this);
    }
}
