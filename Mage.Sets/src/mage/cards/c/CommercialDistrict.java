package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommercialDistrict extends CardImpl {

    public CommercialDistrict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.FOREST);

        // ({T}: Add {R} or {G}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // Commercial District enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Commercial District enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private CommercialDistrict(final CommercialDistrict card) {
        super(card);
    }

    @Override
    public CommercialDistrict copy() {
        return new CommercialDistrict(this);
    }
}
