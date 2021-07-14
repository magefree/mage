package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Aeromoeba extends CardImpl {

    public Aeromoeba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Discard a card: Switch Aeromoeba's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), new DiscardCardCost()
        ));
    }

    private Aeromoeba(final Aeromoeba card) {
        super(card);
    }

    @Override
    public Aeromoeba copy() {
        return new Aeromoeba(this);
    }
}
