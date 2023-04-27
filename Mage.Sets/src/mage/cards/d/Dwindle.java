
package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BlocksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class Dwindle extends CardImpl {

    public Dwindle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets -6/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-6, 0)));

        // When enchanted creature blocks, destroy it.
        this.addAbility(new DwindleTriggeredAbility());
    }

    private Dwindle(final Dwindle card) {
        super(card);
    }

    @Override
    public Dwindle copy() {
        return new Dwindle(this);
    }
}

class DwindleTriggeredAbility extends BlocksAttachedTriggeredAbility {

    DwindleTriggeredAbility() {
        super(new DestroyAttachedToEffect(""), "", false);
    }

    DwindleTriggeredAbility(final DwindleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DwindleTriggeredAbility copy() {
        return new DwindleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When enchanted creature blocks, destroy it.";
    }
}
