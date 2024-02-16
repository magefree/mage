package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FaerieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaerieFormation extends CardImpl {

    public FaerieFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{U}: Create a 1/1 blue Faerie creature token with flying. Draw a card.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new FaerieToken()), new ManaCostsImpl<>("{3}{U}"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("Draw a card"));
        this.addAbility(ability);
    }

    private FaerieFormation(final FaerieFormation card) {
        super(card);
    }

    @Override
    public FaerieFormation copy() {
        return new FaerieFormation(this);
    }
}
