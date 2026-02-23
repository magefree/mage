package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.BeholdCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MudbuttonCursetosser extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent controls with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public MudbuttonCursetosser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast this spell, behold a Goblin or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                "behold a Goblin or pay {2}",
                new BeholdCost(SubType.GOBLIN), new GenericManaCost(2)
        ));

        // This creature can't block.
        this.addAbility(new CantBlockAbility());

        // When this creature dies, destroy target creature an opponent controls with power 2 or less.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MudbuttonCursetosser(final MudbuttonCursetosser card) {
        super(card);
    }

    @Override
    public MudbuttonCursetosser copy() {
        return new MudbuttonCursetosser(this);
    }
}
