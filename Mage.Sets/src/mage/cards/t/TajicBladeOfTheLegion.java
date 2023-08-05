
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;

/**
 *
 * @author jeffwadsworth
 */
public final class TajicBladeOfTheLegion extends CardImpl {

    public TajicBladeOfTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tajic, Blade of the Legion is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        
        // Battalion - Whenever Tajic and at least two other creatures attack, Tajic gets +5/+5 until end of turn.
        this.addAbility(new BattalionAbility(new BoostSourceEffect(5, 5, Duration.EndOfTurn)));
        
    }

    private TajicBladeOfTheLegion(final TajicBladeOfTheLegion card) {
        super(card);
    }

    @Override
    public TajicBladeOfTheLegion copy() {
        return new TajicBladeOfTheLegion(this);
    }
}
