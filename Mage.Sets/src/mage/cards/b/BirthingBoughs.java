package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ShapeshifterToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BirthingBoughs extends CardImpl {

    public BirthingBoughs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {4}, {T}: Create a 2/2 colorless Shapeshifter creature token with changeling.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ShapeshifterToken()), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BirthingBoughs(final BirthingBoughs card) {
        super(card);
    }

    @Override
    public BirthingBoughs copy() {
        return new BirthingBoughs(this);
    }
}
