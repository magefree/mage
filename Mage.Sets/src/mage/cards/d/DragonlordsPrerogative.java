package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevealedOrControlledDragonCondition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DragonlordsPrerogative extends CardImpl {

    public DragonlordsPrerogative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // As an additional cost to cast Dragonlord's Prerogative, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addCost(new RevealDragonFromHandCost());

        // If you revealed a Dragon card or controlled a Dragon as you cast Dragonlord's Prerogative, Dragonlord's Prerogative can't be countered.        
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK,
                new ConditionalContinuousRuleModifyingEffect(
                        new CantBeCounteredSourceEffect(), RevealedOrControlledDragonCondition.instance
                ).setText("if you revealed a Dragon card or controlled a Dragon as you cast this spell, this spell can't be countered")
        ), new DragonOnTheBattlefieldWhileSpellWasCastWatcher());

        // Draw four cards.        
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private DragonlordsPrerogative(final DragonlordsPrerogative card) {
        super(card);
    }

    @Override
    public DragonlordsPrerogative copy() {
        return new DragonlordsPrerogative(this);
    }
}
