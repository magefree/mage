package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HavocJester extends CardImpl {

    public HavocJester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you sacrifice a permanent, Havoc Jester deals 1 damage to any target.
        Ability ability = new SacrificePermanentTriggeredAbility(new DamageTargetEffect(1), StaticFilters.FILTER_PERMANENT);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private HavocJester(final HavocJester card) {
        super(card);
    }

    @Override
    public HavocJester copy() {
        return new HavocJester(this);
    }
}
