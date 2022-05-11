package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class BrashTaunter extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BrashTaunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Brash Taunter is dealt damage, it deals that much damage to target opponent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH, "it"), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {2}{R}, {T}: Brash Taunter fights another target creature.
        ability = new SimpleActivatedAbility(new FightTargetSourceEffect(), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BrashTaunter(final BrashTaunter card) {
        super(card);
    }

    @Override
    public BrashTaunter copy() {
        return new BrashTaunter(this);
    }
}
