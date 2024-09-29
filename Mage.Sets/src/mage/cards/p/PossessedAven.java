package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PossessedAven extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public PossessedAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Possessed Aven gets +1/+1, is black, and has "{2}{B}, {tap}: Destroy target blue creature."
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "As long as seven or more cards are in your graveyard, {this} gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new BecomesColorSourceEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield),
                ThresholdCondition.instance, ", is black"
        ));
        Ability gainedAbility = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        gainedAbility.addCost(new TapSourceCost());
        gainedAbility.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(gainedAbility), ThresholdCondition.instance,
                ", and has \"{2}{B}, {T}: Destroy target blue creature.\""
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private PossessedAven(final PossessedAven card) {
        super(card);
    }

    @Override
    public PossessedAven copy() {
        return new PossessedAven(this);
    }
}
