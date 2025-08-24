package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KoskunFalls extends CardImpl {

    public KoskunFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.supertype.add(SuperType.WORLD);

        // At the beginning of your upkeep, sacrifice Koskun Falls unless you tap an untapped creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE))
        ));

        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield, new GenericManaCost(2)
        )));
    }

    private KoskunFalls(final KoskunFalls card) {
        super(card);
    }

    @Override
    public KoskunFalls copy() {
        return new KoskunFalls(this);
    }
}
