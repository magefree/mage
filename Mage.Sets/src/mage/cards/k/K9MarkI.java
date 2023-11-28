package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class K9MarkI extends CardImpl {

    public K9MarkI(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Negative -- As long as K-9, Mark I is untapped, other legendary creatures you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_LEGENDARY, true
                ), SourceTappedCondition.UNTAPPED, "as long as {this} is untapped, " +
                "other legendary creatures you control have ward {1}"
        )).withFlavorWord("Negative"));

        // Affirmative -- {1}{U}, {T}: Target legendary creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_LEGENDARY));
        this.addAbility(ability.withFlavorWord("Affirmative"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private K9MarkI(final K9MarkI card) {
        super(card);
    }

    @Override
    public K9MarkI copy() {
        return new K9MarkI(this);
    }
}
