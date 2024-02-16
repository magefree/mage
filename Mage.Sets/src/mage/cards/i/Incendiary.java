package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class Incendiary extends CardImpl {
    
    private static final String rule = "{this} deals X damage to any target, where X is the number of fuse counters on {this}.";
    
    public Incendiary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, you may put a fuse counter on Incendiary.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.FUSE.createInstance(), true), TargetController.YOU, true));

        // When enchanted creature dies, Incendiary deals X damage to any target, where X is the number of fuse counters on Incendiary.
        Effect effect = new DamageTargetEffect(new CountersSourceCount(CounterType.FUSE)).setText(rule);
        Ability ability2 = new DiesAttachedTriggeredAbility(effect, "enchanted creature");
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability2);
        
    }
    
    private Incendiary(final Incendiary card) {
        super(card);
    }
    
    @Override
    public Incendiary copy() {
        return new Incendiary(this);
    }
}
