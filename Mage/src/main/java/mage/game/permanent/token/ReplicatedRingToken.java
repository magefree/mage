package mage.game.permanent.token;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class ReplicatedRingToken extends TokenImpl {

    public ReplicatedRingToken() {
        super("Replicated Ring", "colorless snow artifact token named Replicated Ring with \"{T}: Add one mana of any color.\"");
        this.supertype.add(SuperType.SNOW);
        cardType.add(CardType.ARTIFACT);

        this.addAbility(new AnyColorManaAbility());
    }

    private ReplicatedRingToken(final ReplicatedRingToken token) {
        super(token);
    }

    @Override
    public ReplicatedRingToken copy() {
        return new ReplicatedRingToken(this);
    }
}
