package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class YoshimaruScrappyStray extends CardImpl {

    private static final FilterPermanent anotherCreature = new FilterControlledCreaturePermanent("another target creature you control");
    private static final FilterCreaturePermanent nonlegendaryCreature = new FilterCreaturePermanent("nonlegendary creature");
    private static final FilterCreaturePermanent opponentCreature = new FilterCreaturePermanent("creature an opponent controls");

    static {
        anotherCreature.add(AnotherPredicate.instance);
        nonlegendaryCreature.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        opponentCreature.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public YoshimaruScrappyStray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Yoshimaru enters, another target creature you control fights up to one target creature an opponent controls.
        Ability enters = new EntersBattlefieldTriggeredAbility(new FightTargetsEffect()
            .setText("another target creature you control fights up to one target creature an opponent controls")
        );
        enters.addTarget(new TargetPermanent(anotherCreature));
        enters.addTarget(new TargetPermanent(0, 1, opponentCreature));
        this.addAbility(enters);

        // {6}: Put a +1/+1 counter on target nonlegendary creature.
        Ability ability = new SimpleActivatedAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            new GenericManaCost(6)
        );
        ability.addTarget(new TargetPermanent(nonlegendaryCreature));
        this.addAbility(ability);
    }

    private YoshimaruScrappyStray(final YoshimaruScrappyStray card) {
        super(card);
    }

    @Override
    public YoshimaruScrappyStray copy() {
        return new YoshimaruScrappyStray(this);
    }
}
