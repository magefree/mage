
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class FireSnake extends CardImpl {

    public FireSnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Fire Snake dies, destroy target land.
        Ability ability = new DiesTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    public FireSnake(final FireSnake card) {
        super(card);
    }

    @Override
    public FireSnake copy() {
        return new FireSnake(this);
    }
}
