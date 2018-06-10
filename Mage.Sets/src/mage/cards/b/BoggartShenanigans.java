
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class BoggartShenanigans extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another Goblin you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.GOBLIN));
    }

    public BoggartShenanigans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        // Whenever another Goblin you control dies, you may have Boggart Shenanigans deal 1 damage to target player.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(1), true, filter, false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    public BoggartShenanigans(final BoggartShenanigans card) {
        super(card);
    }

    @Override
    public BoggartShenanigans copy() {
        return new BoggartShenanigans(this);
    }
}
