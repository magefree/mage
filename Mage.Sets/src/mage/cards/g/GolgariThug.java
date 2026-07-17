
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jonubuu
 */
public final class GolgariThug extends CardImpl {

    public GolgariThug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Golgari Thug dies, put target creature card from your graveyard on top of your library.
        Ability ability = new DiesSourceTriggeredAbility(new PutOnLibraryTargetEffect(true));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
        // Dredge 4
        this.addAbility(new DredgeAbility(4));
    }

    private GolgariThug(final GolgariThug card) {
        super(card);
    }

    @Override
    public GolgariThug copy() {
        return new GolgariThug(this);
    }
}
