package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.BatToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeeringOnlooker extends CardImpl {

    public LeeringOnlooker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{B}{B}, Exile Leering Onlooker from your graveyard: Create two tapped 1/1 black Bat creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new CreateTokenEffect(new BatToken(), 2, true),
                new ManaCostsImpl<>("{2}{B}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private LeeringOnlooker(final LeeringOnlooker card) {
        super(card);
    }

    @Override
    public LeeringOnlooker copy() {
        return new LeeringOnlooker(this);
    }
}
