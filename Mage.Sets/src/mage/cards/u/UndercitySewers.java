package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercitySewers extends CardImpl {

    public UndercitySewers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.SWAMP);

        // ({T}: Add {U} or {B}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // Undercity Sewers enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Undercity Sewers enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private UndercitySewers(final UndercitySewers card) {
        super(card);
    }

    @Override
    public UndercitySewers copy() {
        return new UndercitySewers(this);
    }
}
