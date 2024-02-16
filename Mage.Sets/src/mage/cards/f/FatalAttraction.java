
package mage.cards.f;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class FatalAttraction extends CardImpl {

    public FatalAttraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Fatal Attraction enters the battlefield, it deals 2 damage to enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAttachedEffect(2, "it")));
        // At the beginning of your upkeep, Fatal Attraction deals 4 damage to enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageAttachedEffect(4), TargetController.YOU, false));
    }

    private FatalAttraction(final FatalAttraction card) {
        super(card);
    }

    @Override
    public FatalAttraction copy() {
        return new FatalAttraction(this);
    }
}
