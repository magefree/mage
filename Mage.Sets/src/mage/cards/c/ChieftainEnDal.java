
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChieftainEnDal extends CardImpl {

    public ChieftainEnDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Chieftain en-Dal attacks, attacking creatures gain first strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES
        ), false));
    }

    private ChieftainEnDal(final ChieftainEnDal card) {
        super(card);
    }

    @Override
    public ChieftainEnDal copy() {
        return new ChieftainEnDal(this);
    }
}
