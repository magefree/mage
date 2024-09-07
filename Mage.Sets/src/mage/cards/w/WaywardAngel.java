package mage.cards.w;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author cbt33, Plopman (Archdemon of Unx)
 */
public final class WaywardAngel extends CardImpl {

    public WaywardAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Wayward Angel gets +3/+3, is black, has trample, and has "At the beginning of your upkeep, sacrifice a creature."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "as long as seven or more cards are in your graveyard, {this} gets +3/+3"));
        ability.addEffect(new ConditionalContinuousEffect(
                new BecomesColorSourceEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield),
                ThresholdCondition.instance, ", is black"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                ThresholdCondition.instance, ", has trample"
        ));
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(
                new SacrificeControllerEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, 1, ""), TargetController.YOU, false
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility), ThresholdCondition.instance,
                "and has \"At the beginning of your upkeep, sacrifice a creature.\""
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private WaywardAngel(final WaywardAngel card) {
        super(card);
    }

    @Override
    public WaywardAngel copy() {
        return new WaywardAngel(this);
    }
}
