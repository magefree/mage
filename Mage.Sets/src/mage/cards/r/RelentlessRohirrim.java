package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelentlessRohirrim extends CardImpl {

    public RelentlessRohirrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Relentless Rohirrim enters the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheRingTemptsYouEffect()));
    }

    private RelentlessRohirrim(final RelentlessRohirrim card) {
        super(card);
    }

    @Override
    public RelentlessRohirrim copy() {
        return new RelentlessRohirrim(this);
    }
}
