
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author Loki
 */
public final class HowlOfTheNightPack extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("for each Forest you control");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
    }

    public HowlOfTheNightPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{G}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new WolfToken(), new PermanentsOnBattlefieldCount(filter)));
    }

    public HowlOfTheNightPack(final HowlOfTheNightPack card) {
        super(card);
    }

    @Override
    public HowlOfTheNightPack copy() {
        return new HowlOfTheNightPack(this);
    }
}
