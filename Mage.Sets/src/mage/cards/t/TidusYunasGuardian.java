package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidusYunasGuardian extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a second target creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(new AnotherTargetPredicate(2));
        filter2.add(CounterAnyPredicate.instance);
    }

    public TidusYunasGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, you may move a counter from target creature you control onto a second target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new MoveCounterTargetsEffect(), true);
        ability.addTarget(new TargetControlledCreaturePermanent().withChooseHint("to take a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).withChooseHint("to move a counter to").setTargetTag(2));
        this.addAbility(ability);

        // Cheer - Whenever one or more creatures you control with counters on them deal combat damage to a player, you may draw a card and proliferate. Do this only once each turn.
        ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), SetTargetPointer.NONE, filter2, true
        ).setDoOnlyOnceEachTurn(true);
        ability.addEffect(new ProliferateEffect(false).concatBy("and"));
        this.addAbility(ability.withFlavorWord("Cheer"));
    }

    private TidusYunasGuardian(final TidusYunasGuardian card) {
        super(card);
    }

    @Override
    public TidusYunasGuardian copy() {
        return new TidusYunasGuardian(this);
    }
}
