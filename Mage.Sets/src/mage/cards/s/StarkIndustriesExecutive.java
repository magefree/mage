package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class StarkIndustriesExecutive extends CardImpl {

    public StarkIndustriesExecutive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{T}: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new TreasureToken()),
            new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private StarkIndustriesExecutive(final StarkIndustriesExecutive card) {
        super(card);
    }

    @Override
    public StarkIndustriesExecutive copy() {
        return new StarkIndustriesExecutive(this);
    }
}
