package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.YoureDealtDamageTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class LivingArtifact extends CardImpl {

    public LivingArtifact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever you're dealt damage, put that many vitality counters on Living Artifact.
        this.addAbility(new YoureDealtDamageTriggeredAbility(new AddCountersSourceEffect(
                CounterType.VITALITY.createInstance(), SavedDamageValue.MANY), false));

        // At the beginning of your upkeep, you may remove a vitality counter from Living Artifact. If you do, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1),
                new RemoveCountersSourceCost(CounterType.VITALITY.createInstance(1)))));
    }

    private LivingArtifact(final LivingArtifact card) {
        super(card);
    }

    @Override
    public LivingArtifact copy() {
        return new LivingArtifact(this);
    }
}
