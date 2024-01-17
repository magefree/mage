package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
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
public final class ShadowyBackstreet extends CardImpl {

    public ShadowyBackstreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {W} or {B}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // Shadowy Backstreet enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Shadowy Backstreet enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private ShadowyBackstreet(final ShadowyBackstreet card) {
        super(card);
    }

    @Override
    public ShadowyBackstreet copy() {
        return new ShadowyBackstreet(this);
    }
}
