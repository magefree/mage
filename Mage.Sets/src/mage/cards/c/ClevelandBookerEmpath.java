package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.WormCantBlockToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class ClevelandBookerEmpath extends CardImpl {

    public ClevelandBookerEmpath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/G}{R/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KWEJIAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, create a 2/2 red and green Worm creature token with "This token can't block."
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new WormCantBlockToken())));

        // {8}: Creatures you control get +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(2, 0, Duration.EndOfTurn), new GenericManaCost(8)));
    }

    private ClevelandBookerEmpath(final ClevelandBookerEmpath card) {
        super(card);
    }

    @Override
    public ClevelandBookerEmpath copy() {
        return new ClevelandBookerEmpath(this);
    }
}
