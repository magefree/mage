package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaucousTheater extends CardImpl {

    public RaucousTheater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {B} or {R}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Raucous Theater enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Raucous Theater enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private RaucousTheater(final RaucousTheater card) {
        super(card);
    }

    @Override
    public RaucousTheater copy() {
        return new RaucousTheater(this);
    }
}
