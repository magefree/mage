package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KiorasDambreaker extends CardImpl {

    public KiorasDambreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Kiora's Dreammaker enters the battlefield, proliferate. (Choose any number of permanents and/or players, then give each a counter of each kind already there.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ProliferateEffect()));
    }

    private KiorasDambreaker(final KiorasDambreaker card) {
        super(card);
    }

    @Override
    public KiorasDambreaker copy() {
        return new KiorasDambreaker(this);
    }
}
