package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.Dragon55Token;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KioraOfFireAndAshes extends CardImpl {

    public KioraOfFireAndAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kiora enters, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Dragon55Token())));

        // {8}: Create a 5/5 red Dragon creature token with flying.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new Dragon55Token()), new ManaCostsImpl<>("{8}")));
    }

    private KioraOfFireAndAshes(final KioraOfFireAndAshes card) {
        super(card);
    }

    @Override
    public KioraOfFireAndAshes copy() {
        return new KioraOfFireAndAshes(this);
    }
}
