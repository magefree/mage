
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
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
public final class OnduChampion extends CardImpl {

    public OnduChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // <i>Rally</i> &mdash; Whenever Ondu Champion or another Ally enters the battlefield under your control, creatures you control gain trample until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), false));
    }

    private OnduChampion(final OnduChampion card) {
        super(card);
    }

    @Override
    public OnduChampion copy() {
        return new OnduChampion(this);
    }
}
