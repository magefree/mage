
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class StaffOfTheWildMagus extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a green spell");
    private static final FilterLandPermanent filterLand = new FilterLandPermanent("a Forest");
    
    static {
        filterSpell.add(new ColorPredicate(ObjectColor.GREEN));
        filterLand.add(SubType.FOREST.getPredicate());
    }

    public StaffOfTheWildMagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast a green spell or a Forest enters the battlefield under your control, you may gain 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(1), filterSpell,false));
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), filterLand, false));
    }

    private StaffOfTheWildMagus(final StaffOfTheWildMagus card) {
        super(card);
    }

    @Override
    public StaffOfTheWildMagus copy() {
        return new StaffOfTheWildMagus(this);
    }
}
