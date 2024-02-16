package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
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
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(
                new CantBeRegeneratedTargetEffect(Duration.EndOfTurn)
                .setText("that creature can't be regenerated this turn")
        ));
    }

    private LimDulsCohort(final LimDulsCohort card) {
        super(card);
    }

    @Override
    public LimDulsCohort copy() {
        return new LimDulsCohort(this);
    }
}
