package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightshellDuo extends CardImpl {

    public LightshellDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Lightshell Duo enters, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));
    }

    private LightshellDuo(final LightshellDuo card) {
        super(card);
    }

    @Override
    public LightshellDuo copy() {
        return new LightshellDuo(this);
    }
}
