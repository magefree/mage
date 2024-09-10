
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SnappingThragg extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that player controls");

    public SnappingThragg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Snapping Thragg deals combat damage to a player, you may have it deal 3 damage to target creature that player controls.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DamageTargetEffect(3), true, true);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster());
        this.addAbility(ability);

        // Morph {4}{R}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{4}{R}{R}")));

    }

    private SnappingThragg(final SnappingThragg card) {
        super(card);
    }

    @Override
    public SnappingThragg copy() {
        return new SnappingThragg(this);
    }
}
