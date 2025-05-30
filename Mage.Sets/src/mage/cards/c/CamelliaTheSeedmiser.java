package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificeOneOrMorePermanentsTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class CamelliaTheSeedmiser extends CardImpl {

    private static final FilterControlledCreaturePermanent filterSquirrelCreatures =
            new FilterControlledCreaturePermanent("Squirrels");

    private static final FilterControlledCreaturePermanent filterSquirrels =
            new FilterControlledCreaturePermanent("other Squirrel you control");

    static {
        filterSquirrelCreatures.add(SubType.SQUIRREL.getPredicate());
        filterSquirrelCreatures.add(AnotherPredicate.instance);
        filterSquirrels.add(SubType.SQUIRREL.getPredicate());
        filterSquirrels.add(AnotherPredicate.instance);
    }

    private static final FilterControlledPermanent filterFood = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public CamelliaTheSeedmiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Other Squirrels you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false),
                Duration.WhileOnBattlefield,
                filterSquirrelCreatures,
                true
        )));
        // Whenever you sacrifice one or more Foods, create a 1/1 green Squirrel creature token.
        this.addAbility(new SacrificeOneOrMorePermanentsTriggeredAbility(new CreateTokenEffect(new SquirrelToken()), filterFood));
        // {2}, Forage: Put a +1/+1 counter on each other Squirrel you control.
        Ability ability = new SimpleActivatedAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterSquirrels), new ManaCostsImpl<>("{2}"));
        ability.addCost(new ForageCost());
        this.addAbility(ability);
    }

    private CamelliaTheSeedmiser(final CamelliaTheSeedmiser card) {
        super(card);
    }

    @Override
    public CamelliaTheSeedmiser copy() {
        return new CamelliaTheSeedmiser(this);
    }
}
