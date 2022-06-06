
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class KnightCaptainOfEos extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a Soldier");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public KnightCaptainOfEos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2), false));
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    private KnightCaptainOfEos(final KnightCaptainOfEos card) {
        super(card);
    }

    @Override
    public KnightCaptainOfEos copy() {
        return new KnightCaptainOfEos(this);
    }
}
