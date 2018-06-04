
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author HCrescent
 */
public final class Mortuary extends CardImpl {


    public Mortuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(new PutOnLibraryTargetEffect(true, "put that card on top of your library."), false, StaticFilters.FILTER_PERMANENT_CREATURE, true, true);
        // Whenever a creature is put into your graveyard from the battlefield, put that card on top of your library.
        this.addAbility(ability);
    }

    public Mortuary(final Mortuary card) {
        super(card);
    }

    @Override
    public Mortuary copy() {
        return new Mortuary(this);
    }
}