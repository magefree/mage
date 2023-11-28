package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnvoyOfOkinecAhau extends CardImpl {

    public EnvoyOfOkinecAhau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {4}{W}: Create a 1/1 colorless Gnome artifact creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new GnomeToken()), new ManaCostsImpl<>("{4}{W}")
        ));
    }

    private EnvoyOfOkinecAhau(final EnvoyOfOkinecAhau card) {
        super(card);
    }

    @Override
    public EnvoyOfOkinecAhau copy() {
        return new EnvoyOfOkinecAhau(this);
    }
}
