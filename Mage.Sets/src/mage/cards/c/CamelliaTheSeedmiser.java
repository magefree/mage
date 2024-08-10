package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SquirrelToken;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.mana.ManaCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class CamelliaTheSeedmiser extends CardImpl {

    private static final FilterControlledCreaturePermanent filterSquirrels =
            new FilterControlledCreaturePermanent("Squirrels");

    static {
        filterSquirrels.add(SubType.SQUIRREL.getPredicate());
        filterSquirrels.add(TargetController.YOU.getControllerPredicate());
        filterSquirrels.add(AnotherPredicate.instance);
    }

    private static final FilterPermanent filterFood = new FilterPermanent(SubType.FOOD, "one or more Foods");


    public CamelliaTheSeedmiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Other Squirrels you control have menace.
        Ability menaceAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,new GainAbilityControlledEffect(
                        new MenaceAbility(false),
                        Duration.WhileOnBattlefield,
                        filterSquirrels,
                        true
                ));
        this.addAbility(menaceAbility);
        // Whenever you sacrifice one or more Foods, create a 1/1 green Squirrel creature token.
        Ability tokenAbility = new SacrificePermanentTriggeredAbility(new CreateTokenEffect(new SquirrelToken(), 1), filterFood);
        this.addAbility(tokenAbility);
        // {2}, Forage: Put a +1/+1 counter on each other Squirrel you control.
        Ability forageAbility = new SimpleActivatedAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterSquirrels), new ManaCostsImpl<>("{2}"));
        forageAbility.addCost(new ForageCost());
        this.addAbility(forageAbility);
    }

    private CamelliaTheSeedmiser(final CamelliaTheSeedmiser card) {
        super(card);
    }

    @Override
    public CamelliaTheSeedmiser copy() {
        return new CamelliaTheSeedmiser(this);
    }
}
