package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TurbulentWetlands extends CardImpl {

    public TurbulentWetlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {U} or {B}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
            .addHint(OpponentsControlEightLandsCondition.getHint()));    }

    private TurbulentWetlands(final TurbulentWetlands card) {
        super(card);
    }

    @Override
    public TurbulentWetlands copy() {
        return new TurbulentWetlands(this);
    }
}
