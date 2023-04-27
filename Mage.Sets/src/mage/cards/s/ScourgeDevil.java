
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class ScourgeDevil extends CardImpl {

    public ScourgeDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Scourge Devil enters the battlefield, creatures you control get +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));
        // Unearth {2}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{R}")));
    }

    private ScourgeDevil(final ScourgeDevil card) {
        super(card);
    }

    @Override
    public ScourgeDevil copy() {
        return new ScourgeDevil(this);
    }
}
