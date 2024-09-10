package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdrixAndNevTwincasters extends CardImpl {

    public AdrixAndNevTwincasters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new CreateTwiceThatManyTokensEffect()));
    }

    private AdrixAndNevTwincasters(final AdrixAndNevTwincasters card) {
        super(card);
    }

    @Override
    public AdrixAndNevTwincasters copy() {
        return new AdrixAndNevTwincasters(this);
    }
}
