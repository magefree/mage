
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class BalthorTheStout extends CardImpl {
    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent(SubType.BARBARIAN, "Barbarian creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.BARBARIAN, "another target Barbarian creature");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public BalthorTheStout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF, SubType.BARBARIAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Barbarian creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));

        // {R}: Another target Barbarian creature gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private BalthorTheStout(final BalthorTheStout card) {
        super(card);
    }

    @Override
    public BalthorTheStout copy() {
        return new BalthorTheStout(this);
    }
}
