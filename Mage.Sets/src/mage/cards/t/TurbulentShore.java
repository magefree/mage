package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.OpponentsControlEightLandsCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TurbulentShore extends CardImpl {

    public TurbulentShore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {W} or {U}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // This land enters tapped unless your opponents control eight or more lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(OpponentsControlEightLandsCondition.instance)
            .addHint(OpponentsControlEightLandsCondition.getHint()));    }

    private TurbulentShore(final TurbulentShore card) {
        super(card);
    }

    @Override
    public TurbulentShore copy() {
        return new TurbulentShore(this);
    }
}
