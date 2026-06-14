package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.VibraniumToken;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class VibraniumMiningMech extends CardImpl {

    public VibraniumMiningMech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever this Vehicle enters or attacks, create a tapped Vibranium token.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new VibraniumToken(), 1, true)
        ));

        // {2}: This Vehicle gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new BoostSourceEffect(1, 0, Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}")
        ));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private VibraniumMiningMech(final VibraniumMiningMech card) {
        super(card);
    }

    @Override
    public VibraniumMiningMech copy() {
        return new VibraniumMiningMech(this);
    }
}
