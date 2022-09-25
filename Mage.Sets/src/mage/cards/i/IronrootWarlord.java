package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronrootWarlord extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public IronrootWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Ironroot Warlord's power is equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(xValue, Duration.EndOfGame)
                        .setText("{this}'s power is equal to the number of creatures you control")
        ));

        // {3}{G}{W}: Create a 1/1 white Soldier creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new SoldierToken()), new ManaCostsImpl<>("{3}{G}{W}")
        ));
    }

    private IronrootWarlord(final IronrootWarlord card) {
        super(card);
    }

    @Override
    public IronrootWarlord copy() {
        return new IronrootWarlord(this);
    }
}
