
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class KorBladewhirl extends CardImpl {

    public KorBladewhirl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Rally</i> &mdash; Whenever Kor Bladewhirl or another Ally enters the battlefield under your control, creatures you control gain first strike until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), false));
    }

    private KorBladewhirl(final KorBladewhirl card) {
        super(card);
    }

    @Override
    public KorBladewhirl copy() {
        return new KorBladewhirl(this);
    }
}
