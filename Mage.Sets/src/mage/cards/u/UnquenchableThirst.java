
package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class UnquenchableThirst extends CardImpl {

    public UnquenchableThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Unquenchable Thirst enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, tap enchanted creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()),
                DesertControlledOrGraveyardCondition.instance, "When {this} enters the battlefield, " +
                "if you control a Desert or there is a Desert card in your graveyard, tap enchanted creature."
        ).addHint(DesertControlledOrGraveyardCondition.getHint()));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    private UnquenchableThirst(final UnquenchableThirst card) {
        super(card);
    }

    @Override
    public UnquenchableThirst copy() {
        return new UnquenchableThirst(this);
    }
}
