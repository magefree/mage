package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureDredger extends CardImpl {

    public TreasureDredger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, {T}, Pay 1 life: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private TreasureDredger(final TreasureDredger card) {
        super(card);
    }

    @Override
    public TreasureDredger copy() {
        return new TreasureDredger(this);
    }
}
