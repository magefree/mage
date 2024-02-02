package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WispdrinkerVampire extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature with power 2 or less");
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public WispdrinkerVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature with power 2 or less enters the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new LoseLifeOpponentsEffect(1), filter);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {5}{W}{B}: Creatures you control with power 2 or less gain deathtouch and lifelink until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityAllEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter2,
                "creatures you control with power 2 or less gain deathtouch"
        ), new ManaCostsImpl<>("{5}{W}{B}"));
        ability.addEffect(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                filter2, "and lifelink until end of turn"
        ));
        this.addAbility(ability);
    }

    private WispdrinkerVampire(final WispdrinkerVampire card) {
        super(card);
    }

    @Override
    public WispdrinkerVampire copy() {
        return new WispdrinkerVampire(this);
    }
}
