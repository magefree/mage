
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WurmToken2;

/**
 *
 * @author LevelX2
 */
public final class ArmadaWurm extends CardImpl {

    public ArmadaWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{W}{W}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Armada Wurm enters the battlefield, create a 5/5 green Wurm creature token with trample.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WurmToken2()), false));

    }

    public ArmadaWurm(final ArmadaWurm card) {
        super(card);
    }

    @Override
    public ArmadaWurm copy() {
        return new ArmadaWurm(this);
    }
}
