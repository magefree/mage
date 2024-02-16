
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.meta.OrTriggeredAbility;
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
public final class StaffOfTheSunMagus extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a white spell");
    private static final FilterLandPermanent filterLand = new FilterLandPermanent("a Plains");
    
    static {
        filterSpell.add(new ColorPredicate(ObjectColor.WHITE));
        filterLand.add(SubType.PLAINS.getPredicate());
    }

    public StaffOfTheSunMagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast a white spell or a Plains enters the battlefield under your control, you gain 1 life.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), false,
                "Whenever you cast a white spell or a Plains enters the battlefield under your control, ",
                new SpellCastControllerTriggeredAbility(null, filterSpell,false),
                new EntersBattlefieldControlledTriggeredAbility(null, filterLand)));
    }

    private StaffOfTheSunMagus(final StaffOfTheSunMagus card) {
        super(card);
    }

    @Override
    public StaffOfTheSunMagus copy() {
        return new StaffOfTheSunMagus(this);
    }
}
