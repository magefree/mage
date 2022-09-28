
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class CallOfTheFullMoon extends CardImpl {

    public CallOfTheFullMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +3/+2 and has trample.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 2, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has trample.");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, sacrifice Call of the Full Moon.
        TriggeredAbility ability2 = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability2, TwoOrMoreSpellsWereCastLastTurnCondition.instance,
            "At the beginning of each upkeep, if a player cast two or more spells last turn, sacrifice {this}."));
    }

    private CallOfTheFullMoon(final CallOfTheFullMoon card) {
        super(card);
    }

    @Override
    public CallOfTheFullMoon copy() {
        return new CallOfTheFullMoon(this);
    }
}
