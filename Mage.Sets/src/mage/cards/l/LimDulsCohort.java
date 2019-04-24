package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.CantBeRegeneratedTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LimDulsCohort extends CardImpl {

    public LimDulsCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Lim-Dûl's Cohort blocks or becomes blocked by a creature, that creature can't be regenerated this turn.
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(
                new CantBeRegeneratedTargetEffect(Duration.EndOfTurn),
                new FilterCreaturePermanent(),
                false,
                "Whenever {this} blocks or becomes blocked by a creature, that creature can't be regenerated this turn.",
                true));

    }

    private LimDulsCohort(final LimDulsCohort card) {
        super(card);
    }

    @Override
    public LimDulsCohort copy() {
        return new LimDulsCohort(this);
    }
}
