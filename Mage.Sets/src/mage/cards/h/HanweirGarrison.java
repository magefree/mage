
package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.RedHumanToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HanweirGarrison extends CardImpl {

    public HanweirGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.meldsWithClazz = mage.cards.h.HanweirBattlements.class;
        this.meldsToClazz = mage.cards.h.HanweirTheWrithingTownship.class;

        // Whenever Hanweir Garrison attacks, create two 1/1 red Human creature tokens tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new RedHumanToken(), 2, true, true), false));

        // <i>(Melds with Hanweir Battlements.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("<i>(Melds with Hanweir Battlements.)</i>")));
    }

    private HanweirGarrison(final HanweirGarrison card) {
        super(card);
    }

    @Override
    public HanweirGarrison copy() {
        return new HanweirGarrison(this);
    }
}
