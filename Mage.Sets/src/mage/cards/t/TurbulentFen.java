package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurbulentFen extends CardImpl {

    public TurbulentFen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {B} or {G}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
                .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentFen(final TurbulentFen card) {
        super(card);
    }

    @Override
    public TurbulentFen copy() {
        return new TurbulentFen(this);
    }
}
