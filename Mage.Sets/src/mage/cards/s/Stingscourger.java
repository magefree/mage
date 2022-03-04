
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class Stingscourger extends CardImpl {

    public Stingscourger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Echo {3}{R}
        this.addAbility(new EchoAbility("{3}{R}"));
        // When Stingscourger enters the battlefield, return target creature an opponent controls to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private Stingscourger(final Stingscourger card) {
        super(card);
    }

    @Override
    public Stingscourger copy() {
        return new Stingscourger(this);
    }
}
