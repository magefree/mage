
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
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
public final class KalastriaNightwatch extends CardImpl {

    public KalastriaNightwatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever you gain life, Kalastria Nightwatch gains flying until end of turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false));
    }

    private KalastriaNightwatch(final KalastriaNightwatch card) {
        super(card);
    }

    @Override
    public KalastriaNightwatch copy() {
        return new KalastriaNightwatch(this);
    }
}
