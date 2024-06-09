
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
public final class StaffOfTheFlameMagus extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a red spell");
    private static final FilterLandPermanent filterLand = new FilterLandPermanent("a Mountain");
    
    static {
        filterSpell.add(new ColorPredicate(ObjectColor.RED));
        filterLand.add(SubType.MOUNTAIN.getPredicate());
    }

    public StaffOfTheFlameMagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you cast a red spell or a Mountain enters the battlefield under your control, you gain 1 life.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), false,
                "Whenever you cast a red spell or a Mountain enters the battlefield under your control, ",
                new SpellCastControllerTriggeredAbility(null, filterSpell,false),
                new EntersBattlefieldControlledTriggeredAbility(null, filterLand)));
    }

    private StaffOfTheFlameMagus(final StaffOfTheFlameMagus card) {
        super(card);
    }

    @Override
    public StaffOfTheFlameMagus copy() {
        return new StaffOfTheFlameMagus(this);
    }
}
