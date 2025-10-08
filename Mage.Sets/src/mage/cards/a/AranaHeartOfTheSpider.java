package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AranaHeartOfTheSpider extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("modified creature you control");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public AranaHeartOfTheSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack, put a +1/+1 counter on target attacking creature.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);

        // Whenever a modified creature you control deals combat damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                filter, false, SetTargetPointer.NONE, true
        ));
    }

    private AranaHeartOfTheSpider(final AranaHeartOfTheSpider card) {
        super(card);
    }

    @Override
    public AranaHeartOfTheSpider copy() {
        return new AranaHeartOfTheSpider(this);
    }
}
