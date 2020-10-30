
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class PardicArsonist extends CardImpl {

    public PardicArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - As long as seven or more cards are in your graveyard, Pardic Arsonist has "When Pardic Arsonist enters the battlefield, it deals 3 damage to any target."
        Ability gainedAbility = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        gainedAbility.addTarget(new TargetAnyTarget());

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(gainedAbility), new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"When {this} enters the battlefield, it deals 3 damage to any target.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public PardicArsonist(final PardicArsonist card) {
        super(card);
    }

    @Override
    public PardicArsonist copy() {
        return new PardicArsonist(this);
    }
}
