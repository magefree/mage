
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class SteadfastCathar extends CardImpl {

    public SteadfastCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Steadfast Cathar attacks, it gets +0/+2 until end of turn.
        Effect effect = new BoostSourceEffect(0, 2, Duration.EndOfTurn);
        effect.setText("it gets +0/+2 until end of turn");
        this.addAbility(new AttacksTriggeredAbility(effect, false));

    }

    private SteadfastCathar(final SteadfastCathar card) {
        super(card);
    }

    @Override
    public SteadfastCathar copy() {
        return new SteadfastCathar(this);
    }
}
