package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class RhinoTerribleTrampler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or land");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.LAND.getPredicate()));
        filter2.add(AnotherPredicate.instance);
    }

    public RhinoTerribleTrampler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Rhino enters, destroy target artifact or land. Distribute three +1/+1 counters among up to three other target creatures. They gain trample until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect().setTargetPointer(new SecondTargetPointer()));
        ability.addEffect(new DistributeCountersEffect()
                .setText("distribute three +1/+1 counters among up to three other target creatures"));
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                "They gain trample until end of turn"
        ));
        ability.addTarget(new TargetCreaturePermanentAmount(3, 0, 3, filter2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private RhinoTerribleTrampler(final RhinoTerribleTrampler card) {
        super(card);
    }

    @Override
    public RhinoTerribleTrampler copy() {
        return new RhinoTerribleTrampler(this);
    }
}
