package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BazaarTrademage extends CardImpl {

    public BazaarTrademage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Bazaar Trademage enters the battlefield, draw two cards, then discard three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(2, 3)));
    }

    private BazaarTrademage(final BazaarTrademage card) {
        super(card);
    }

    @Override
    public BazaarTrademage copy() {
        return new BazaarTrademage(this);
    }
}
