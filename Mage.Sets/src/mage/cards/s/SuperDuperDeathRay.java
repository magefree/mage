package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageWithExcessEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuperDuperDeathRay extends CardImpl {

    public SuperDuperDeathRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Trample
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "Trample <i>(This spell can deal excess damage to its target's controller.)</i>"
        )));

        // Super-Duper Death Ray deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageWithExcessEffect(4)
                .setText("{this} deals 4 damage to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuperDuperDeathRay(final SuperDuperDeathRay card) {
        super(card);
    }

    @Override
    public SuperDuperDeathRay copy() {
        return new SuperDuperDeathRay(this);
    }
}
