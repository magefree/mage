package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
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
public final class ThunderingFalls extends CardImpl {

    public ThunderingFalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U} or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Thundering Falls enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Thundering Falls enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private ThunderingFalls(final ThunderingFalls card) {
        super(card);
    }

    @Override
    public ThunderingFalls copy() {
        return new ThunderingFalls(this);
    }
}
