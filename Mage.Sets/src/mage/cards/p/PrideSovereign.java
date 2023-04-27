
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.CatToken2;

/**
 *
 * @author LevelX2
 */
public final class PrideSovereign extends CardImpl {

    public PrideSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Pride Sovereign gets +1/+1 for each other Cat you control.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.CAT, "other Cat you control");
        filter.add(AnotherPredicate.instance);
        DynamicValue otherCats = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(otherCats, otherCats, Duration.WhileOnBattlefield)));
        // {W}, {t}, Exert Pride Sovereign: Create two 1/1 white Cat creature tokens with lifelink.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CatToken2(), 2), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        this.addAbility(ability);
    }

    private PrideSovereign(final PrideSovereign card) {
        super(card);
    }

    @Override
    public PrideSovereign copy() {
        return new PrideSovereign(this);
    }

}
