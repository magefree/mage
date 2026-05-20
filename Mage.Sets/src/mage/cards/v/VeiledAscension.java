package mage.cards.v;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.constants.*;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;

import java.util.UUID;

/**
 * @author greenlovecat
 */

public final class VeiledAscension extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("face-down creature you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public VeiledAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Veiled Ascension enters the battlefield, put a flying counter on each face-down creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.FLYING.createInstance(), filter)
        ));

        // Face-down creatures you control enter the battlefield with a flying counter on them.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersWithCountersControlledEffect(
                filter, CounterType.FLYING.createInstance(), false
        ).setText("Face-down creatures you control enter with a flying counter on them")));

        // At the beginning of your upkeep, you may cloak the top card of your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ManifestEffect(StaticValue.get(1), false, true), true));
    }

    private VeiledAscension(final VeiledAscension card) {
        super(card);
    }

    @Override
    public VeiledAscension copy() {
        return new VeiledAscension(this);
    }

}
