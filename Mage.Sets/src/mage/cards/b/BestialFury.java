

package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author 18ths
 */
public final class BestialFury extends CardImpl {

    public BestialFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability enchantAbility = new EnchantAbility(auraTarget);
        this.addAbility(enchantAbility);
        
        // When Bestial Fury enters the battlefield, draw a card at the beginning of the next turn's upkeep.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse)), false));
        
        // Whenever enchanted creature becomes blocked, it gets +4/+0 and gains trample until end of turn.
        Ability pumpAbility = new BecomesBlockedAttachedTriggeredAbility(new BoostEnchantedEffect(4, 0, Duration.EndOfTurn), false);
        pumpAbility.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn));
        
        
        this.addAbility(pumpAbility);
        
    }

    private BestialFury(final BestialFury card) {
        super(card);
    }

    @Override
    public BestialFury copy() {
        return new BestialFury(this);
    }
}
