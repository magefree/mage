

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 * @author Loki
 */
public final class JadeIdol extends CardImpl {

    public JadeIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.addAbility(new SpellCastControllerTriggeredAbility(new BecomesCreatureSourceEffect(new JadeIdolToken(), "", Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private JadeIdol(final JadeIdol card) {
        super(card);
    }

    @Override
    public JadeIdol copy() {
        return new JadeIdol(this);
    }

}

class JadeIdolToken extends TokenImpl {
    JadeIdolToken() {
        super("", "4/4 Spirit artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
    public JadeIdolToken(final JadeIdolToken token) {
        super(token);
    }

    public JadeIdolToken copy() {
        return new JadeIdolToken(this);
    }
}