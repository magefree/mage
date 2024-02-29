package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulTracker extends CardImpl {

    public VengefulTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent sacrifices an artifact, Vengeful Tracker deals 2 damage to them.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "them"),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, TargetController.OPPONENT, SetTargetPointer.PLAYER, false
        ));
    }

    private VengefulTracker(final VengefulTracker card) {
        super(card);
    }

    @Override
    public VengefulTracker copy() {
        return new VengefulTracker(this);
    }
}
