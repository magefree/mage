

package mage.cards.q;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, North
 */
public final class QuagSickness extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SWAMP));
    private static final Hint hint = new ValueHint("Swamps you control", xValue);

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp you control");

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public QuagSickness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter, -1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(amount, amount, Duration.WhileOnBattlefield)));
        this.getSpellAbility().addHint(hint);

    }

    private QuagSickness(final QuagSickness card) {
        super(card);
    }

    @Override
    public QuagSickness copy() {
        return new QuagSickness(this);
    }
}
