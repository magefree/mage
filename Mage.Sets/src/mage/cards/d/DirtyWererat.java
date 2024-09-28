package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class DirtyWererat extends CardImpl {

    public DirtyWererat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {B}, Discard a card: Regenerate Dirty Wererat.
        Ability ability = new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Threshold - As long as seven or more cards are in your graveyard, Dirty Wererat gets +2/+2 and can't block.
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

    private DirtyWererat(final DirtyWererat card) {
        super(card);
    }

    @Override
    public DirtyWererat copy() {
        return new DirtyWererat(this);
    }
}
