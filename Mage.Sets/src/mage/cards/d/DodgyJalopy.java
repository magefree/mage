package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ScavengeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DodgyJalopy extends CardImpl {

    public DodgyJalopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Dodgy Jalopy's power is equal to the highest mana value among creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(DodgyJalopyValue.instance)
        ));

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // Scavenge {2}{G}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{2}{G}")));
    }

    private DodgyJalopy(final DodgyJalopy card) {
        super(card);
    }

    @Override
    public DodgyJalopy copy() {
        return new DodgyJalopy(this);
    }
}

enum DodgyJalopyValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public DodgyJalopyValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the highest mana value among creatures you control";
    }
}
