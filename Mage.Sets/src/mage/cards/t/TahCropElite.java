
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class TahCropElite extends CardImpl {

    public TahCropElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may exert Tah-Crop Elite as it attacks. When you do, creatures you control get +1/+1 until end of turn.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        this.addAbility(new ExertAbility(ability));
    }

    private TahCropElite(final TahCropElite card) {
        super(card);
    }

    @Override
    public TahCropElite copy() {
        return new TahCropElite(this);
    }
}
