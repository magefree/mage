
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class LyevSkyknight extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls");
 
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }
    
    public LyevSkyknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        


        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Lyev Skyknight enters the battlefield, detain target nonland permanent an opponent controls.
        // (Until your next turn, that permanent can't attack or block and its activated abilities can't be activated.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetainTargetEffect(), false);
        TargetNonlandPermanent target = new TargetNonlandPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private LyevSkyknight(final LyevSkyknight card) {
        super(card);
    }

    @Override
    public LyevSkyknight copy() {
        return new LyevSkyknight(this);
    }
}
