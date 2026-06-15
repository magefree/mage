package mage.cards.i;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ItsClobberinTime extends CardImpl {

    public ItsClobberinTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Choose one --
        // * Target creature you control deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // * Destroy target artifact or enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
            .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT))
        );

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private ItsClobberinTime(final ItsClobberinTime card) {
        super(card);
    }

    @Override
    public ItsClobberinTime copy() {
        return new ItsClobberinTime(this);
    }
}
