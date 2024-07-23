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
import mage.abilities.keyword.TrampleAbility;
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
public final class PossessedCentaur extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public PossessedCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Threshold - As long as seven or more cards are in your graveyard, Possessed Centaur gets +1/+1, is black, and has "{2}{B}, {tap}: Destroy target green creature."
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
                ", and has \"{2}{B}, {T}: Destroy target green creature.\""
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private PossessedCentaur(final PossessedCentaur card) {
        super(card);
    }

    @Override
    public PossessedCentaur copy() {
        return new PossessedCentaur(this);
    }
}
