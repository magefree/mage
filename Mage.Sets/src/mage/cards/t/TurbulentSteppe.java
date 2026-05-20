package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurbulentSteppe extends CardImpl {

    public TurbulentSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {R} or {W}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
                .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentSteppe(final TurbulentSteppe card) {
        super(card);
    }

    @Override
    public TurbulentSteppe copy() {
        return new TurbulentSteppe(this);
    }
}
