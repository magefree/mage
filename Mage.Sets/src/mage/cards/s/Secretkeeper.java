
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class Secretkeeper extends CardImpl {

    public Secretkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you have more cards in hand than each opponent, Secretkeeper gets +2/+2 and has flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2,2, Duration.WhileOnBattlefield),
                MoreCardsInHandThanOpponentsCondition.instance,
                "As long as you have more cards in hand than each opponent, Secretkeeper gets +2/+2"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                MoreCardsInHandThanOpponentsCondition.instance,
                "and has flying"));
        this.addAbility(ability);
    }

    private Secretkeeper(final Secretkeeper card) {
        super(card);
    }

    @Override
    public Secretkeeper copy() {
        return new Secretkeeper(this);
    }
}
