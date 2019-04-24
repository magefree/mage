
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class OgreBattledriver extends CardImpl {
    
    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }
    
    private static final String rule = "Whenever another creature enters the battlefield under your control, that creature gets +2/+0 and gains haste until end of turn.";

    public OgreBattledriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield under your control, that creature gets +2/+0 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), filter, false, SetTargetPointer.PERMANENT, rule, true);
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
        
    }

    public OgreBattledriver(final OgreBattledriver card) {
        super(card);
    }

    @Override
    public OgreBattledriver copy() {
        return new OgreBattledriver(this);
    }
}
