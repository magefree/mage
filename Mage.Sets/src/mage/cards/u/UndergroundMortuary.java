package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
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
public final class UndergroundMortuary extends CardImpl {

    public UndergroundMortuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {B} or {G}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // Underground Mortuary enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Underground Mortuary enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private UndergroundMortuary(final UndergroundMortuary card) {
        super(card);
    }

    @Override
    public UndergroundMortuary copy() {
        return new UndergroundMortuary(this);
    }
}
