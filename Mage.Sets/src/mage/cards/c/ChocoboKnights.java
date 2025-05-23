package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChocoboKnights extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public ChocoboKnights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack, creatures you control with counters on them gain double strike until end of turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter
        ), 1));
    }

    private ChocoboKnights(final ChocoboKnights card) {
        super(card);
    }

    @Override
    public ChocoboKnights copy() {
        return new ChocoboKnights(this);
    }
}
