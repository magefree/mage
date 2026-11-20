package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.KlingonWarriorToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheDurasSisters extends CardImpl {

    public TheDurasSisters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When The Duras Sisters die, create a 2/1 red Klingon Warrior creature token with haste.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new KlingonWarriorToken())));
    }

    private TheDurasSisters(final TheDurasSisters card) {
        super(card);
    }

    @Override
    public TheDurasSisters copy() {
        return new TheDurasSisters(this);
    }
}
