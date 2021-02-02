
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BorosElite extends CardImpl {

    public BorosElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Battalion - Whenever Boros Elite and at least two other creatures attack, Boros Elite gets +2/+2 until end of turn.
        this.addAbility(new BattalionAbility(new BoostSourceEffect(2,2, Duration.EndOfTurn)));
    }

    private BorosElite(final BorosElite card) {
        super(card);
    }

    @Override
    public BorosElite copy() {
        return new BorosElite(this);
    }
}
