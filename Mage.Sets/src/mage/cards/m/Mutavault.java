package mage.cards.m;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class Mutavault extends CardImpl {

    public Mutavault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 creature with all creature types")
                        .withAllCreatureTypes(true),
                CardType.LAND, Duration.EndOfTurn
        ), new GenericManaCost(1)));
    }

    private Mutavault(final Mutavault card) {
        super(card);
    }

    @Override
    public Mutavault copy() {
        return new Mutavault(this);
    }
}
