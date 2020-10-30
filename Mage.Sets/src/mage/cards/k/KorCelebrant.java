package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KorCelebrant extends CardImpl {

    public KorCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Kor Celebrant or another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        ));
    }

    private KorCelebrant(final KorCelebrant card) {
        super(card);
    }

    @Override
    public KorCelebrant copy() {
        return new KorCelebrant(this);
    }
}
