package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SoldierArtifactToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class SiegeVeteran extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SOLDIER, "another nontoken Soldier you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SiegeVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever another nontoken Soldier you control dies, create a 1/1 colorless Soldier artifact creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new SoldierArtifactToken()),
                false, filter
        ));
    }

    private SiegeVeteran(final SiegeVeteran card) {
        super(card);
    }

    @Override
    public SiegeVeteran copy() {
        return new SiegeVeteran(this);
    }
}
