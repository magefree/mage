
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureAttachedTriggeredAbility;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Charisma extends CardImpl {
    
    private static final String rule = "gain control of the other creature for as long as {this} remains on the battlefield";

    public Charisma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to a creature, gain control of the other creature for as long as Charisma remains on the battlefield.
        ConditionalContinuousEffect conditionalEffect = new ConditionalContinuousEffect(new GainControlTargetEffect(Duration.Custom), 
                new SourceRemainsInZoneCondition(Zone.BATTLEFIELD), rule);
        this.addAbility(new DealsDamageToACreatureAttachedTriggeredAbility(conditionalEffect, false, "enchanted creature", false, true));
        
    }

    private Charisma(final Charisma card) {
        super(card);
    }

    @Override
    public Charisma copy() {
        return new Charisma(this);
    }
}
