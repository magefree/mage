package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
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
public final class BogBadger extends CardImpl {

    public BogBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {B}
        this.addAbility(new KickerAbility("{B}"));

        // When Bog Badger enters the battlefield, if it was kicked, creatures you control gain menace until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        )).withInterveningIf(KickedCondition.ONCE));
    }

    private BogBadger(final BogBadger card) {
        super(card);
    }

    @Override
    public BogBadger copy() {
        return new BogBadger(this);
    }
}
