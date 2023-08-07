
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Loki
 */
public final class HalcyonGlaze extends CardImpl {

    public HalcyonGlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever you cast a creature spell, Halcyon Glaze becomes a 4/4 Illusion creature with flying in addition to its other types until end of turn.
        Effect effect = new BecomesCreatureSourceEffect(new HalcyonGlazeToken(), CardType.ENCHANTMENT, Duration.EndOfTurn);
        this.addAbility(new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_CREATURE, false));
    }

    private HalcyonGlaze(final HalcyonGlaze card) {
        super(card);
    }

    @Override
    public HalcyonGlaze copy() {
        return new HalcyonGlaze(this);
    }
}

class HalcyonGlazeToken extends TokenImpl {

    HalcyonGlazeToken() {
        super("", "4/4 Illusion creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
    }
    public HalcyonGlazeToken(final HalcyonGlazeToken token) {
        super(token);
    }

    public HalcyonGlazeToken copy() {
        return new HalcyonGlazeToken(this);
    }
}
