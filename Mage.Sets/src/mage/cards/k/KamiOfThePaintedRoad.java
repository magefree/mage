
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainProtectionFromColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class KamiOfThePaintedRoad extends CardImpl {

    public KamiOfThePaintedRoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, Kami of the Painted Road gains protection from the color of your choice until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainProtectionFromColorSourceEffect(Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));

    }

    private KamiOfThePaintedRoad(final KamiOfThePaintedRoad card) {
        super(card);
    }

    @Override
    public KamiOfThePaintedRoad copy() {
        return new KamiOfThePaintedRoad(this);
    }
}
