
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author cbt33
 */
public final class MysticVisionary extends CardImpl {

    public MysticVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.subtype.add(SubType.MYSTIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Threshold - Mystic Visionary has flying as long as seven or more cards are in your graveyard.
       Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
           new GainAbilitySourceEffect(FlyingAbility.getInstance()), new CardsInControllerGraveyardCondition(7),
           "{this} has flying as long as seven or more cards are in your graveyard."));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
       this.addAbility(ability);
    }

    private MysticVisionary(final MysticVisionary card) {
        super(card);
    }

    @Override
    public MysticVisionary copy() {
        return new MysticVisionary(this);
    }
}
