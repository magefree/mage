package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZukoExiledPrince extends CardImpl {

    public ZukoExiledPrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Firebending 3
        this.addAbility(new FirebendingAbility(3));

        // {3}: Exile the top card of your library. You may play that card this turn.
        this.addAbility(new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn), new GenericManaCost(3)
        ));
    }

    private ZukoExiledPrince(final ZukoExiledPrince card) {
        super(card);
    }

    @Override
    public ZukoExiledPrince copy() {
        return new ZukoExiledPrince(this);
    }
}
