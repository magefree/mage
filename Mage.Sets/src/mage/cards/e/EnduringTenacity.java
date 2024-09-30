package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EnduringGlimmerTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringTenacity extends CardImpl {

    public EnduringTenacity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you gain life, target opponent loses that much life.
        Ability ability = new GainLifeControllerTriggeredAbility(new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Enduring Tenacity dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new EnduringGlimmerTriggeredAbility());
    }

    private EnduringTenacity(final EnduringTenacity card) {
        super(card);
    }

    @Override
    public EnduringTenacity copy() {
        return new EnduringTenacity(this);
    }
}
