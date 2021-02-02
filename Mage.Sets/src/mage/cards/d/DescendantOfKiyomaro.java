
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class DescendantOfKiyomaro extends CardImpl {

    public DescendantOfKiyomaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as you have more cards in hand than each opponent, Descendant of Kiyomaro gets +1/+2 and has "Whenever this creature deals combat damage, you gain 3 life."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1,2, Duration.WhileOnBattlefield),
                MoreCardsInHandThanOpponentsCondition.instance,
                "As long as you have more cards in hand than each opponent, {this} gets +1/+2"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new DealsCombatDamageTriggeredAbility(new GainLifeEffect(3), false)),
                MoreCardsInHandThanOpponentsCondition.instance,
                "and has \"Whenever this creature deals combat damage, you gain 3 life.\""));
        this.addAbility(ability);
    }

    private DescendantOfKiyomaro(final DescendantOfKiyomaro card) {
        super(card);
    }

    @Override
    public DescendantOfKiyomaro copy() {
        return new DescendantOfKiyomaro(this);
    }
}
