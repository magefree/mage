package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.Spirit32Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllustriousHistorian extends CardImpl {

    public IllustriousHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {5}, Exile Illustrious Historian from your graveyard: Create a tapped 3/2 red and white Spirit creature token.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new CreateTokenEffect(
                        new Spirit32Token(), 1, true, false
                ), new GenericManaCost(5)
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private IllustriousHistorian(final IllustriousHistorian card) {
        super(card);
    }

    @Override
    public IllustriousHistorian copy() {
        return new IllustriousHistorian(this);
    }
}
