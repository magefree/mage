
package mage.cards.i;

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
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IsperiasSkywatch extends CardImpl {
    
    public IsperiasSkywatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Isperia's Skywatch enters the battlefield, detain target creature an opponent controls.
        // (Until your next turn, that creature can't attack or block and its activated abilities can't be activated.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetainTargetEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private IsperiasSkywatch(final IsperiasSkywatch card) {
        super(card);
    }

    @Override
    public IsperiasSkywatch copy() {
        return new IsperiasSkywatch(this);
    }
}
