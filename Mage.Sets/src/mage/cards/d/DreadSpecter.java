package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author djbrez
 */
public final class DreadSpecter extends CardImpl {

    public DreadSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dread Specter blocks or becomes blocked by a nonblack creature, destroy that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(effect, StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK, false));
    }

    private DreadSpecter(final DreadSpecter card) {
        super(card);
    }

    @Override
    public DreadSpecter copy() {
        return new DreadSpecter(this);
    }
}
