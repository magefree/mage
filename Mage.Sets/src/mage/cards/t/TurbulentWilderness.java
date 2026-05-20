package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurbulentWilderness extends CardImpl {

    public TurbulentWilderness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {G} or {U}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
                .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentWilderness(final TurbulentWilderness card) {
        super(card);
    }

    @Override
    public TurbulentWilderness copy() {
        return new TurbulentWilderness(this);
    }
}
