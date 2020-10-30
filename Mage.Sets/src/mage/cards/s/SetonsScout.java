
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class SetonsScout extends CardImpl {

    public SetonsScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Threshold - Seton's Scout gets +2/+2 as long as seven or more cards are in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} gets +2/+2"));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public SetonsScout(final SetonsScout card) {
        super(card);
    }

    @Override
    public SetonsScout copy() {
        return new SetonsScout(this);
    }
}
