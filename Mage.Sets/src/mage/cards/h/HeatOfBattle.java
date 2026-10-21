package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

public final class HeatOfBattle extends CardImpl {

    public HeatOfBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");


        // Whenever a creature blocks, Heat of Battle deals 1 damage to that creature's controller.
        this.addAbility(new BlocksAllTriggeredAbility(
                new DamageTargetEffect(1).withTargetDescription("that creature's controller"),
                false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER
        ));
    }

    private HeatOfBattle(final HeatOfBattle card) {
        super(card);
    }

    public HeatOfBattle copy() {
        return new HeatOfBattle(this);
    }
}

