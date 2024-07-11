package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class VigilantSentry extends CardImpl {

    public VigilantSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Threshold - As long as seven or more cards are in your graveyard, Vigilant Sentry gets +1/+1 and has "{tap}: Target attacking or blocking creature gets +3/+3 until end of turn."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "As long as seven or more cards are in your graveyard, {this} gets +1/+1"
        ));
        Ability gainedAbility = new SimpleActivatedAbility(
                new BoostTargetEffect(3, 3, Duration.EndOfTurn), new TapSourceCost()
        );
        gainedAbility.addTarget(new TargetAttackingOrBlockingCreature());
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility), ThresholdCondition.instance,
                "and has \"{T}: Target attacking or blocking creature gets +3/+3 until end of turn.\""
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private VigilantSentry(final VigilantSentry card) {
        super(card);
    }

    @Override
    public VigilantSentry copy() {
        return new VigilantSentry(this);
    }
}
