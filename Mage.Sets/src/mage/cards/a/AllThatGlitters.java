package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AllThatGlitters extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public AllThatGlitters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each artifact and/or enchantment you control.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(
                xValue, xValue, Duration.WhileOnBattlefield
        ).setText("enchanted creature gets +1/+1 for each artifact and/or enchantment you control")));
    }

    private AllThatGlitters(final AllThatGlitters card) {
        super(card);
    }

    @Override
    public AllThatGlitters copy() {
        return new AllThatGlitters(this);
    }
}
// someBODY
