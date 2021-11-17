
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class ScaledHulk extends CardImpl {

    public ScaledHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // Whenever you cast a Spirit or Arcane spell, Scaled Hulk gets +2/+2 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private ScaledHulk(final ScaledHulk card) {
        super(card);
    }

    @Override
    public ScaledHulk copy() {
        return new ScaledHulk(this);
    }
}
