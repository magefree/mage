
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class BeastOfBurden extends CardImpl {

    public BeastOfBurden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Beast of Burden's power and toughness are each equal to the number of creatures on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(new FilterCreaturePermanent("creatures on the battlefield")), Duration.EndOfGame)));
        
    }

    private BeastOfBurden(final BeastOfBurden card) {
        super(card);
    }

    @Override
    public BeastOfBurden copy() {
        return new BeastOfBurden(this);
    }
}
