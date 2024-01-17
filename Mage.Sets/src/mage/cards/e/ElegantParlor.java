package mage.cards.e;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
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
public final class ElegantParlor extends CardImpl {

    public ElegantParlor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {R} or {W}.)
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Elegant Parlor enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Elegant Parlor enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private ElegantParlor(final ElegantParlor card) {
        super(card);
    }

    @Override
    public ElegantParlor copy() {
        return new ElegantParlor(this);
    }
}
