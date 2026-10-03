package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TurbulentCrater extends CardImpl {

    public TurbulentCrater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {B} or {R}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
            .addHint(OpponentsControlEightLandsCondition.getHint()));
    }

    private TurbulentCrater(final TurbulentCrater card) {
        super(card);
    }

    @Override
    public TurbulentCrater copy() {
        return new TurbulentCrater(this);
    }
}
