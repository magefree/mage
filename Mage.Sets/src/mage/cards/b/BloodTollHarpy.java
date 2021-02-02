
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BloodTollHarpy extends CardImpl {

    public BloodTollHarpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HARPY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Blood-Toll Harpy enters the battlefield, each player loses 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeAllPlayersEffect(1)));
    }

    private BloodTollHarpy(final BloodTollHarpy card) {
        super(card);
    }

    @Override
    public BloodTollHarpy copy() {
        return new BloodTollHarpy(this);
    }
}
