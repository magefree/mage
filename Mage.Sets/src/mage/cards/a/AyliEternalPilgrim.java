package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AyliEternalPilgrim extends CardImpl {

    public AyliEternalPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR, SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {1}, Sacrifice another creature: You gain life equal to the sacrificed creature's toughness.
        Effect effect = new GainLifeEffect(SacrificeCostCreaturesToughness.instance);
        effect.setText("You gain life equal to the sacrificed creature's toughness");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);

        // {1}{W}{B}, Sacrifice another creature: Exile target nonland permanent. Activate only if you have at least 10 life more than your starting life total.
        ability = new ActivateIfConditionActivatedAbility(
                new ExileTargetEffect(), new ManaCostsImpl<>("{1}{W}{B}"), MoreThanStartingLifeTotalCondition.TEN
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetNonlandPermanent().withChooseHint("to exile"));
        this.addAbility(ability);
    }

    private AyliEternalPilgrim(final AyliEternalPilgrim card) {
        super(card);
    }

    @Override
    public AyliEternalPilgrim copy() {
        return new AyliEternalPilgrim(this);
    }
}
