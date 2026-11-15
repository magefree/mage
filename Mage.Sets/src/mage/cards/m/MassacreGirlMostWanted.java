package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.OpponentDealtNoncombatDamageTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class MassacreGirlMostWanted extends CardImpl {

    private static final FilterCreatureOrPlaneswalkerPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MassacreGirlMostWanted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever another creature or planeswalker you control dies, Massacre Girl deals 1 damage to target opponent and you gain 1 life.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(1), false, filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent is dealt noncombat damage, put a +1/+1 counter on Massacre Girl.
        this.addAbility(new OpponentDealtNoncombatDamageTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));
    }

    private MassacreGirlMostWanted(final MassacreGirlMostWanted card) {
        super(card);
    }

    @Override
    public MassacreGirlMostWanted copy() {
        return new MassacreGirlMostWanted(this);
    }
}
