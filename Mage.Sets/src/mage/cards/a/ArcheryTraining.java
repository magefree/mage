package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author jeffwadsworth
 */
public final class ArcheryTraining extends CardImpl {

    private static final String rule = "This creature deals X damage to target attacking or blocking creature, where X is the number of arrow counters on {this}.";

    public ArcheryTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, you may put an arrow counter on Archery Training.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.ARROW.createInstance(), true), TargetController.YOU, true));

        // Enchanted creature has "{tap}: This creature deals X damage to target attacking or blocking creature, where X is the number of arrow counters on Archery Training."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new CountersSourceCount(CounterType.ARROW)).setText(rule), new TapSourceCost());
        gainedAbility.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA)));

    }

    public ArcheryTraining(final ArcheryTraining card) {
        super(card);
    }

    @Override
    public ArcheryTraining copy() {
        return new ArcheryTraining(this);
    }
}
