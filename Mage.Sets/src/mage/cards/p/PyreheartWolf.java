package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author intimidatingant
 */
public final class PyreheartWolf extends CardImpl {

    public PyreheartWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Pyreheart Wolf attacks, creatures you control gain menace until end of turn. (They can't be blocked except by two or more creatures.)
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES), false));

        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        this.addAbility(new UndyingAbility());

    }

    private PyreheartWolf(final PyreheartWolf card) {
        super(card);
    }

    @Override
    public PyreheartWolf copy() {
        return new PyreheartWolf(this);
    }
}
