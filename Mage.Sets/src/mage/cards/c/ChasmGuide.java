
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class ChasmGuide extends CardImpl {

    public ChasmGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Rally</i> &mdash; Whenever Chasm Guide or another Ally enters the battlefield under your control, creatures you control gain haste until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), false));
    }

    private ChasmGuide(final ChasmGuide card) {
        super(card);
    }

    @Override
    public ChasmGuide copy() {
        return new ChasmGuide(this);
    }
}
