
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FireDragon extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("for each Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public FireDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Fire Dragon enters the battlefield, it deals damage equal to the number of Mountains you control to target creature.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("it deals damage equal to the number of Mountains you control to target creature");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FireDragon(final FireDragon card) {
        super(card);
    }

    @Override
    public FireDragon copy() {
        return new FireDragon(this);
    }
}
