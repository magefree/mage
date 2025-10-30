package mage.game.permanent.token;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author TheElk801
 */
public final class MutavaultToken extends TokenImpl {

    public MutavaultToken() {
        super("Mutavault", "Mutavault token");
        cardType.add(CardType.LAND);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 creature with all creature types")
                        .withAllCreatureTypes(true),
                CardType.LAND, Duration.EndOfTurn
        ), new GenericManaCost(1)));
    }

    private MutavaultToken(final MutavaultToken token) {
        super(token);
    }

    public MutavaultToken copy() {
        return new MutavaultToken(this);
    }
}
