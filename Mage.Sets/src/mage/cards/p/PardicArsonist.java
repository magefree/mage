package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PardicArsonist extends CardImpl {

    public PardicArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Threshold - As long as seven or more cards are in your graveyard, Pardic Arsonist has "When Pardic Arsonist enters the battlefield, it deals 3 damage to any target."
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ability), ThresholdCondition.instance, "As long as " +
                "seven or more cards are in your graveyard, {this} has \"When {this} " +
                "enters the battlefield, it deals 3 damage to any target.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private PardicArsonist(final PardicArsonist card) {
        super(card);
    }

    @Override
    public PardicArsonist copy() {
        return new PardicArsonist(this);
    }
}
