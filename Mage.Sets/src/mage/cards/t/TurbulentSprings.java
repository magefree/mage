package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurbulentSprings extends CardImpl {

    public TurbulentSprings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U} or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
                .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentSprings(final TurbulentSprings card) {
        super(card);
    }

    @Override
    public TurbulentSprings copy() {
        return new TurbulentSprings(this);
    }
}
