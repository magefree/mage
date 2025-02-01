package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessConditionSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HazoretGodseeker extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public HazoretGodseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {1}, {T}: Target creature with power 2 or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Hazoret can't attack or block unless you have max speed.
        this.addAbility(new SimpleStaticAbility(new CantAttackBlockUnlessConditionSourceEffect(HazoretGodseekerCondition.instance)));
    }

    private HazoretGodseeker(final HazoretGodseeker card) {
        super(card);
    }

    @Override
    public HazoretGodseeker copy() {
        return new HazoretGodseeker(this);
    }
}

enum HazoretGodseekerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ControllerSpeedCount.instance.calculate(game, source, null) >= 4;
    }

    @Override
    public String toString() {
        return "you have max speed";
    }
}
