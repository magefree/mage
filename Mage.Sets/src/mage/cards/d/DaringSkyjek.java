
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class DaringSkyjek extends CardImpl {

    public DaringSkyjek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Battalion — Whenever Daring Skyjek and at least two other creatures attack, Daring Skyjek gains flying until end of turn.
        this.addAbility(new BattalionAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)));
    }

    private DaringSkyjek(final DaringSkyjek card) {
        super(card);
    }

    @Override
    public DaringSkyjek copy() {
        return new DaringSkyjek(this);
    }
}
