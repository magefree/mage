package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuthlessWinnower extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Elf creature");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
    }

    public RuthlessWinnower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each player's upkeep, that player sacrifices a non-Elf creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false
        ));
    }

    private RuthlessWinnower(final RuthlessWinnower card) {
        super(card);
    }

    @Override
    public RuthlessWinnower copy() {
        return new RuthlessWinnower(this);
    }
}
