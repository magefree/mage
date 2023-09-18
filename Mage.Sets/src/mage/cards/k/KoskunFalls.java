
package mage.cards.k;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class KoskunFalls extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("untapped creature you control");
    
    static {
        filterCreature.add(TappedPredicate.UNTAPPED);
    }

    public KoskunFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.supertype.add(SuperType.WORLD);

        // At the beginning of your upkeep, sacrifice Koskun Falls unless you tap an untapped creature you control.
        Effect effect = new SacrificeSourceUnlessPaysEffect(new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filterCreature, true)));
        effect.setText("sacrifice Koskun Falls unless you tap an untapped creature you control");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false));

        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        this.addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield,
                new ManaCostsImpl<>("{2}")
            )
        ));
    }

    private KoskunFalls(final KoskunFalls card) {
        super(card);
    }

    @Override
    public KoskunFalls copy() {
        return new KoskunFalls(this);
    }
}
