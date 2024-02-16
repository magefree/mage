
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author TheElk801
 */
public final class SecurityDetail extends CardImpl {

    public SecurityDetail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // {W}{W}: Put a 1/1 white Soldier creature token onto the battlefield. Activate this ability only if you control no creatures and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new SoldierToken()),
                new ManaCostsImpl<>("{W}{W}"),
                1,
                new CreatureCountCondition(0, TargetController.YOU)
        ));
    }

    private SecurityDetail(final SecurityDetail card) {
        super(card);
    }

    @Override
    public SecurityDetail copy() {
        return new SecurityDetail(this);
    }
}
