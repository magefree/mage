
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class NirkanaAssassin extends CardImpl {

    public NirkanaAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you gain life, Nirkana Assassin gains deathtouch until end of turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), false));
    }

    private NirkanaAssassin(final NirkanaAssassin card) {
        super(card);
    }

    @Override
    public NirkanaAssassin copy() {
        return new NirkanaAssassin(this);
    }
}
