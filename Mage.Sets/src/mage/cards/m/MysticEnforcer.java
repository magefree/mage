
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MysticEnforcer extends CardImpl {

    public MysticEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.MYSTIC);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        // Threshold - As long as seven or more cards are in your graveyard, Mystic Enforcer gets +3/+3 and has flying.
        Ability thresholdAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            " as long as seven or more cards are in your graveyard, {this} gets +3/+3"));
        thresholdAbility.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            new CardsInControllerGraveyardCondition(7), "and has flying"));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    public MysticEnforcer(final MysticEnforcer card) {
        super(card);
    }

    @Override
    public MysticEnforcer copy() {
        return new MysticEnforcer(this);
    }
}
