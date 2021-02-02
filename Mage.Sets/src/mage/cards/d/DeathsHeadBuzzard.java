
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
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
public final class DeathsHeadBuzzard extends CardImpl {

    public DeathsHeadBuzzard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Death's-Head Buzzard dies, all creatures get -1/-1 until end of turn.
        this.addAbility(new DiesSourceTriggeredAbility(new BoostAllEffect(-1, -1, Duration.EndOfTurn)));
    }

    private DeathsHeadBuzzard(final DeathsHeadBuzzard card) {
        super(card);
    }

    @Override
    public DeathsHeadBuzzard copy() {
        return new DeathsHeadBuzzard(this);
    }
}
