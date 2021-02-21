
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class BullRushBruiser extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "another Warrior");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BullRushBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Bull-Rush Bruiser attacks, if your team controls another Warrior, Bull-Rush Bruiser gains first strike until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "Whenever {this} attacks, if your team controls another Warrior, "
                + "{this} gains first strike until end of turn."
        ));
    }

    private BullRushBruiser(final BullRushBruiser card) {
        super(card);
    }

    @Override
    public BullRushBruiser copy() {
        return new BullRushBruiser(this);
    }
}
