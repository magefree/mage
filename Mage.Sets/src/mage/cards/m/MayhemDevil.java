package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MayhemDevil extends CardImpl {

    public MayhemDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player sacrifices a permanent, Mayhem Devil deals 1 damage to any target.
        Ability ability = new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1), StaticFilters.FILTER_PERMANENT,
                TargetController.ANY, SetTargetPointer.NONE, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MayhemDevil(final MayhemDevil card) {
        super(card);
    }

    @Override
    public MayhemDevil copy() {
        return new MayhemDevil(this);
    }
}
