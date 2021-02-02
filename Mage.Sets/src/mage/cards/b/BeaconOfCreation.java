
package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author North
 */
public final class BeaconOfCreation extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public BeaconOfCreation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Create a 1/1 green Insect creature token for each Forest you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectToken(), new PermanentsOnBattlefieldCount(filter)));
        // Shuffle Beacon of Creation into its owner's library.
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private BeaconOfCreation(final BeaconOfCreation card) {
        super(card);
    }

    @Override
    public BeaconOfCreation copy() {
        return new BeaconOfCreation(this);
    }
}
