
package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class GauntletOfMight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Red creatures");
    private static final FilterLandPermanent filterMountain = new FilterLandPermanent("a Mountain is tapped");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
    }
    
    public GauntletOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Red creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));  
        
        // Whenever a Mountain is tapped for mana, its controller adds {R}.
        ManaEffect effect = new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.R), "their");
        effect.setText("its controller adds {R}");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect, filterMountain, SetTargetPointer.PLAYER));        
    }

    private GauntletOfMight(final GauntletOfMight card) {
        super(card);
    }

    @Override
    public GauntletOfMight copy() {
        return new GauntletOfMight(this);
    }
}
