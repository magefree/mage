

package mage.cards.k;

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
 * @author Loki
 */
public final class KamiOfTheHunt extends CardImpl {

    public KamiOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private KamiOfTheHunt(final KamiOfTheHunt card) {
        super(card);
    }

    @Override
    public KamiOfTheHunt copy() {
        return new KamiOfTheHunt(this);
    }

}
