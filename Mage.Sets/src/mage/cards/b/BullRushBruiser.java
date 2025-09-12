package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BullRushBruiser extends CardImpl {

    private static final FilterTeamPermanent filter
            = new FilterTeamPermanent(SubType.WARRIOR, "your team controls another Warrior");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public BullRushBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Bull-Rush Bruiser attacks, if your team controls another Warrior, Bull-Rush Bruiser gains first strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        )).withInterveningIf(condition));
    }

    private BullRushBruiser(final BullRushBruiser card) {
        super(card);
    }

    @Override
    public BullRushBruiser copy() {
        return new BullRushBruiser(this);
    }
}
