package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.FishToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpendableLackey extends CardImpl {

    public ExpendableLackey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{U}, Exile Expendable Lackey from your graveyard: Create a 1/1 blue Fish creature token with "This creature can't be blocked." Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new FishToken()), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private ExpendableLackey(final ExpendableLackey card) {
        super(card);
    }

    @Override
    public ExpendableLackey copy() {
        return new ExpendableLackey(this);
    }
}
