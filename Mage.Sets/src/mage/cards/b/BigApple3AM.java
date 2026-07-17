package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RatToken;

/**
 *
 * @author muz
 */
public final class BigApple3AM extends CardImpl {

    public BigApple3AM(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped. As it enters, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(new AddManaChosenColorEffect(), new TapSourceCost()));

        // {5}, {T}: Create a 1/1 black Rat creature token for each opponent you have.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new RatToken(), OpponentsCount.instance)
                .setText("create a 1/1 black Rat creature token for each opponent you have"),
            new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BigApple3AM(final BigApple3AM card) {
        super(card);
    }

    @Override
    public BigApple3AM copy() {
        return new BigApple3AM(this);
    }
}
