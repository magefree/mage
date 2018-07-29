package mage.cards.g;


import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoatToken;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class GoatHerder extends CardImpl {

    public GoatHerder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FARMER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //When Goat Herder enters the battlefield, create a 0/1 white Goat creature token.
        this.addAbility(new EntersBattlefieldAbility(new CreateTokenEffect(new GoatToken(), 1)));

    }

    public GoatHerder(final GoatHerder card) {
        super(card);
    }

    @Override
    public GoatHerder copy() {
        return new GoatHerder(this);
    }

}
