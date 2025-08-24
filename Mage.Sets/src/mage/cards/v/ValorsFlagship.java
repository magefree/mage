package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.PilotSaddleCrewToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValorsFlagship extends CardImpl {

    public ValorsFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Crew 3
        this.addAbility(new CrewAbility(3));

        // Cycling {X}{2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{2}{W}")));

        // When you cycle this card, create X 1/1 colorless Pilot creature tokens with "This token saddles Mounts and crews Vehicles as though its power were 2 greater."
        this.addAbility(new CycleTriggeredAbility(
                new CreateTokenEffect(new PilotSaddleCrewToken(), new EffectKeyValue("cycleXValue", "X"))));
    }

    private ValorsFlagship(final ValorsFlagship card) {
        super(card);
    }

    @Override
    public ValorsFlagship copy() {
        return new ValorsFlagship(this);
    }
}
