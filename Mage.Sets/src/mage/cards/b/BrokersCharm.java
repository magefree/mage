package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrokersCharm extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BrokersCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}{U}");

        // Choose one —
        // • Target creature you control gets +1/+0 until end of turn. It deals damage equal to its power to target creature or planeswalker an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // • Draw two cards.
        this.getSpellAbility().addMode(new Mode(new DrawCardSourceControllerEffect(2)));
    }

    private BrokersCharm(final BrokersCharm card) {
        super(card);
    }

    @Override
    public BrokersCharm copy() {
        return new BrokersCharm(this);
    }
}
