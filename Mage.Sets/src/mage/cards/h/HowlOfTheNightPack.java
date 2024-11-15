
package mage.cards.h;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HowlOfTheNightPack extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public HowlOfTheNightPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new WolfToken(), new PermanentsOnBattlefieldCount(filter)));
        this.getSpellAbility().addHint(hint);
    }

    private HowlOfTheNightPack(final HowlOfTheNightPack card) {
        super(card);
    }

    @Override
    public HowlOfTheNightPack copy() {
        return new HowlOfTheNightPack(this);
    }
}
