
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class FlayerDrone extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another colorless creature");
    
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public FlayerDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Whenever another colorless creature enters the battlefield under your control, target opponent loses 1 life.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), filter, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private FlayerDrone(final FlayerDrone card) {
        super(card);
    }

    @Override
    public FlayerDrone copy() {
        return new FlayerDrone(this);
    }
}
