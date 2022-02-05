package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraftedGrowth extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("creature or Vehicle you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public GraftedGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Grafted Growth enters the battlefield, put a +1/+1 counter on target creature or Vehicle you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Enchanted land has "{T}: Add two mana of any one color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
        ), AttachmentType.AURA).setText("enchanted land has \"{T}: Add two mana of any one color.\"")));
    }

    private GraftedGrowth(final GraftedGrowth card) {
        super(card);
    }

    @Override
    public GraftedGrowth copy() {
        return new GraftedGrowth(this);
    }
}
