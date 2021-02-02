
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EtherswornShieldmage extends CardImpl {

    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");
    
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }
    
    public EtherswornShieldmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);



        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ethersworn Shieldmage enters the battlefield, prevent all damage that would be dealt to artifact creatures this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter), false));
    }

    private EtherswornShieldmage(final EtherswornShieldmage card) {
        super(card);
    }

    @Override
    public EtherswornShieldmage copy() {
        return new EtherswornShieldmage(this);
    }
}
