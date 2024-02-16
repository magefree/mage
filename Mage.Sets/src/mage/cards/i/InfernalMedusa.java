
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author L_J
 */
public final class InfernalMedusa extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public InfernalMedusa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Infernal Medusa blocks a creature, destroy that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new BlocksCreatureTriggeredAbility(effect));
        // Whenever Infernal Medusa becomes blocked by a non-Wall creature, destroy that creature at end of combat.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(effect, filter, false));
    }

    private InfernalMedusa(final InfernalMedusa card) {
        super(card);
    }

    @Override
    public InfernalMedusa copy() {
        return new InfernalMedusa(this);
    }
}
