package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurbulentMoor extends CardImpl {

    public TurbulentMoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W} or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
                .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentMoor(final TurbulentMoor card) {
        super(card);
    }

    @Override
    public TurbulentMoor copy() {
        return new TurbulentMoor(this);
    }
}
