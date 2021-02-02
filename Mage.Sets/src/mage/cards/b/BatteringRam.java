
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class BatteringRam extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public BatteringRam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, Battering Ram gains banding until end of combat.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilitySourceEffect(BandingAbility.getInstance(), Duration.EndOfCombat), TargetController.YOU, false));

        // Whenever Battering Ram becomes blocked by a Wall, destroy that Wall at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that Wall at end of combat");
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(effect, filter, false));
    }

    private BatteringRam(final BatteringRam card) {
        super(card);
    }

    @Override
    public BatteringRam copy() {
        return new BatteringRam(this);
    }
}
