
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author LevelX2
 */
public final class MarduAscendancy extends CardImpl {

    private static final FilterControlledCreaturePermanent attackFilter = new FilterControlledCreaturePermanent("nontoken creature you control");
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        attackFilter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MarduAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{W}{B}");

        // Whenever a nontoken creature you control attacks, create a 1/1 red Goblin creature token tapped and attacking.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 1, true, true), false, attackFilter));

        // Sacrifice Mardu Ascendancy: Creatures you control get +0/+3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(0, 3, Duration.EndOfTurn, filter, false),
                new SacrificeSourceCost()));
    }

    private MarduAscendancy(final MarduAscendancy card) {
        super(card);
    }

    @Override
    public MarduAscendancy copy() {
        return new MarduAscendancy(this);
    }
}
