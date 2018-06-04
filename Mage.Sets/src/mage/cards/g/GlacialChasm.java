
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author Plopman
 */
public final class GlacialChasm extends CardImpl {

    public GlacialChasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Cumulative upkeep-Pay 2 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(2)));
        // When Glacial Chasm enters the battlefield, sacrifice a land.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, "")));
        // Creatures you control can't attack.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
        filter.add(new ControllerPredicate(TargetController.YOU));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, filter)));
        // Prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToControllerEffect(Duration.WhileOnBattlefield)));
    }

    public GlacialChasm(final GlacialChasm card) {
        super(card);
    }

    @Override
    public GlacialChasm copy() {
        return new GlacialChasm(this);
    }
}
