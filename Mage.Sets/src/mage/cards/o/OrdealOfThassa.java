
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.condition.common.AttachedToCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OrdealOfThassa extends CardImpl {

    public OrdealOfThassa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Whenever enchanted creature attacks, put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice Ordeal of Thassa.
        ability = new AttacksAttachedTriggeredAbility(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(),"it"), AttachmentType.AURA, false);
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(), new AttachedToCounterCondition(CounterType.P1P1, 3),
                "Then if it has three or more +1/+1 counters on it, sacrifice {this}"));
        this.addAbility(ability);
        // When you sacrifice Ordeal of Thassa, draw two cards.
        this.addAbility(new SacrificeSourceTriggeredAbility(new DrawCardSourceControllerEffect(2), false));

    }

    private OrdealOfThassa(final OrdealOfThassa card) {
        super(card);
    }

    @Override
    public OrdealOfThassa copy() {
        return new OrdealOfThassa(this);
    }
}
