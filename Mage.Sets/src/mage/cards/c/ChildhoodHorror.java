package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class ChildhoodHorror extends CardImpl {

    public ChildhoodHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Childhood Horror gets +2/+2 and can't block.
        Ability thresholdAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                ThresholdCondition.instance, "If seven or more cards are in your graveyard, {this} gets +2/+2"
        ));
        thresholdAbility.addEffect(new ConditionalRestrictionEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield), ThresholdCondition.instance
        ).setText("and can't block"));
        thresholdAbility.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(thresholdAbility);
    }

    private ChildhoodHorror(final ChildhoodHorror card) {
        super(card);
    }

    @Override
    public ChildhoodHorror copy() {
        return new ChildhoodHorror(this);
    }
}
