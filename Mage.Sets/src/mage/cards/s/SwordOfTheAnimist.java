
package mage.cards.s;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class SwordOfTheAnimist extends CardImpl {

    public SwordOfTheAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));
        // Whenever equipped creature attacks, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.addAbility(new AttacksAttachedTriggeredAbility(new SearchLibraryPutInPlayEffect(target, true), true));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private SwordOfTheAnimist(final SwordOfTheAnimist card) {
        super(card);
    }

    @Override
    public SwordOfTheAnimist copy() {
        return new SwordOfTheAnimist(this);
    }
}
