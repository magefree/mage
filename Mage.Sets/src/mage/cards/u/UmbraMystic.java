
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.AttachedToControlledPermanentPredicate;

/**
 *
 * @author North & L_J
 */
public final class UmbraMystic extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Auras attached to permanents you control");

    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(new AttachedToControlledPermanentPredicate());
    }

    public UmbraMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Auras attached to permanents you control have totem armor.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new TotemArmorAbility(), Duration.WhileOnBattlefield, filter, false)));
    }

    private UmbraMystic(final UmbraMystic card) {
        super(card);
    }

    @Override
    public UmbraMystic copy() {
        return new UmbraMystic(this);
    }
}
