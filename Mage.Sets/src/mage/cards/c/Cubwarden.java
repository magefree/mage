package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CatToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cubwarden extends CardImpl {

    public Cubwarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Mutate {2}{W}{W}
        this.addAbility(new MutateAbility(this, "{2}{W}{W}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever this creature mutates, create two 1/1 white Cat creature tokens with lifelink.
        this.addAbility(new MutatesSourceTriggeredAbility(new CreateTokenEffect(new CatToken2(), 2)));
    }

    private Cubwarden(final Cubwarden card) {
        super(card);
    }

    @Override
    public Cubwarden copy() {
        return new Cubwarden(this);
    }
}
