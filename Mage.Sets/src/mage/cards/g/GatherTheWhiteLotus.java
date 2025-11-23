package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatherTheWhiteLotus extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.PLAINS));
    private static final Hint hint = new ValueHint("Plains you control", xValue);

    public GatherTheWhiteLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Create a 1/1 white Ally creature token for each Plains you control. Scry 2.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new AllyToken(), xValue));
        this.getSpellAbility().addEffect(new ScryEffect(2));
        this.getSpellAbility().addHint(hint);
    }

    private GatherTheWhiteLotus(final GatherTheWhiteLotus card) {
        super(card);
    }

    @Override
    public GatherTheWhiteLotus copy() {
        return new GatherTheWhiteLotus(this);
    }
}
