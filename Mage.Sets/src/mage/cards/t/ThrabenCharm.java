package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThrabenCharm extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(CreaturesYouControlCount.PLURAL, 2);

    public ThrabenCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one --
        // * Thraben Charm deals damage equal to twice the number of creatures you control to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to twice the number of creatures you control to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);

        // * Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // * Exile any number of target players' graveyards.
        mode = new Mode(new ExileGraveyardAllTargetPlayerEffect());
        mode.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.getSpellAbility().addMode(mode);
    }

    private ThrabenCharm(final ThrabenCharm card) {
        super(card);
    }

    @Override
    public ThrabenCharm copy() {
        return new ThrabenCharm(this);
    }
}
