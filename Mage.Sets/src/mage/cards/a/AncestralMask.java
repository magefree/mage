package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AncestralMask extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("other enchantment on the battlefield");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AncestralMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 for each other enchantment on the battlefield.
        PermanentsOnBattlefieldCount countEnchantments = new PermanentsOnBattlefieldCount(filter, 2);
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(countEnchantments, countEnchantments, Duration.WhileOnBattlefield)));
    }

    private AncestralMask(final AncestralMask card) {
        super(card);
    }

    @Override
    public AncestralMask copy() {
        return new AncestralMask(this);
    }
}
