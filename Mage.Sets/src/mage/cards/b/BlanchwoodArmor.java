
package mage.cards.b;

import mage.abilities.Ability;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BlanchwoodArmor extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public BlanchwoodArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));
        this.getSpellAbility().addHint(hint);
    }

    private BlanchwoodArmor(final BlanchwoodArmor card) {
        super(card);
    }

    @Override
    public BlanchwoodArmor copy() {
        return new BlanchwoodArmor(this);
    }
}
