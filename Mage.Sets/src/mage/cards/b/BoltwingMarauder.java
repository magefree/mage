package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BoltwingMarauder extends CardImpl {
    
    public BoltwingMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever another creature enters the battlefield under your control, target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(2,0, Duration.EndOfTurn), StaticFilters.FILTER_ANOTHER_CREATURE,false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BoltwingMarauder(final BoltwingMarauder card) {
        super(card);
    }

    @Override
    public BoltwingMarauder copy() {
        return new BoltwingMarauder(this);
    }
}
