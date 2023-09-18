package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
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
public final class Glittermonger extends CardImpl {

    public Glittermonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: Create a Treasure token.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new TapSourceCost()));
    }

    private Glittermonger(final Glittermonger card) {
        super(card);
    }

    @Override
    public Glittermonger copy() {
        return new Glittermonger(this);
    }
}
