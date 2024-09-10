package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.LastTimeCounterRemovedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DeadlyGrubInsectToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class DeadlyGrub extends CardImpl {

    public DeadlyGrub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Deadly Grub dies, if it had no time counters on it, create a 6/1 green Insect creature token with shroud.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new DeadlyGrubInsectToken(), 1)),
                LastTimeCounterRemovedCondition.instance, "When {this} dies, if it had no time counters on it, create a 6/1 green Insect creature token with shroud."));
    }

    private DeadlyGrub(final DeadlyGrub card) {
        super(card);
    }

    @Override
    public DeadlyGrub copy() {
        return new DeadlyGrub(this);
    }
}
