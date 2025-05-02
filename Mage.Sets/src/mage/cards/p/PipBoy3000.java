package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author Cguy7777
 */
public final class PipBoy3000 extends CardImpl {

    public PipBoy3000(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, choose one --
        // * Sort Inventory -- Draw a card, then discard a card.
        Ability ability = new AttacksAttachedTriggeredAbility(new DrawDiscardControllerEffect())
                .withFirstModeFlavorWord("Sort Inventory");

        // * Pick a Perk -- Put a +1/+1 counter on that creature.
        ability.addMode(new Mode(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "that creature"))
                .withFlavorWord("Pick a Perk"));

        // * Check Map -- Untap up to two target lands.
        Mode mode = new Mode(new UntapTargetEffect());
        mode.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_LANDS));
        ability.addMode(mode.withFlavorWord("Check Map"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private PipBoy3000(final PipBoy3000 card) {
        super(card);
    }

    @Override
    public PipBoy3000 copy() {
        return new PipBoy3000(this);
    }
}
