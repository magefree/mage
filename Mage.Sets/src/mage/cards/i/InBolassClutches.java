
package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSuperTypeAttachedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class InBolassClutches extends CardImpl {

    public InBolassClutches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("permanent")));

        // Enchanted permanent is legendary.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new AddCardSuperTypeAttachedEffect(SuperType.LEGENDARY, Duration.WhileOnBattlefield, AttachmentType.AURA)
        ));
    }

    private InBolassClutches(final InBolassClutches card) {
        super(card);
    }

    @Override
    public InBolassClutches copy() {
        return new InBolassClutches(this);
    }
}
