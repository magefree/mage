package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KithkinGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClachanFestival extends CardImpl {

    public ClachanFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.KITHKIN);

        // When this enchantment enters, create two 1/1 green and white Kithkin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinGreenWhiteToken(), 2)));

        // {4}{W}: Create a 1/1 green and white Kithkin creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new KithkinGreenWhiteToken()), new ManaCostsImpl<>("{4}{W}")
        ));
    }

    private ClachanFestival(final ClachanFestival card) {
        super(card);
    }

    @Override
    public ClachanFestival copy() {
        return new ClachanFestival(this);
    }
}
