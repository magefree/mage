package mage.cards.m;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class MastersRebuke extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public MastersRebuke(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control deals damage equal to its power to target creature or planeswalker you don’t control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        TargetPermanent targetForDamage = new TargetPermanent(filter);
        targetForDamage.setTargetTag(2);
        this.getSpellAbility().addTarget(targetForDamage);
    }

    private MastersRebuke(final MastersRebuke card) { super(card); }

    @Override
    public MastersRebuke copy() { return new MastersRebuke(this); }
}