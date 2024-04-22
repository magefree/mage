package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RubyDaringTracker extends CardImpl {

    public RubyDaringTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Ruby, Daring Tracker attacks while you control a creature with power 4 or greater, Ruby gets +2/+2 until end of turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false),
                FerociousCondition.instance,
                "Whenever {this} attacks while you control a creature with power 4 or greater, {this} gets +2/+2 until end of turn."
        ));

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private RubyDaringTracker(final RubyDaringTracker card) {
        super(card);
    }

    @Override
    public RubyDaringTracker copy() {
        return new RubyDaringTracker(this);
    }
}
