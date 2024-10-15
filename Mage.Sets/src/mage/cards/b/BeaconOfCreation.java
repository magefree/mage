
package mage.cards.b;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author North
 */
public final class BeaconOfCreation extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public BeaconOfCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");


        // Create a 1/1 green Insect creature token for each Forest you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectToken(), new PermanentsOnBattlefieldCount(filter)));
        // Shuffle Beacon of Creation into its owner's library.
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
        this.getSpellAbility().addHint(hint);
    }

    private BeaconOfCreation(final BeaconOfCreation card) {
        super(card);
    }

    @Override
    public BeaconOfCreation copy() {
        return new BeaconOfCreation(this);
    }
}
