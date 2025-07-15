package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UthrosScanship extends CardImpl {

    public UthrosScanship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawDiscardControllerEffect(2, 1)
        ));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // 4/4
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(4, 4));
    }

    private UthrosScanship(final UthrosScanship card) {
        super(card);
    }

    @Override
    public UthrosScanship copy() {
        return new UthrosScanship(this);
    }
}
