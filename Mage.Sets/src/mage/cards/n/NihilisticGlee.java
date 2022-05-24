package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NihilisticGlee extends CardImpl {

    public NihilisticGlee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // {2}{B}, Discard a card: Target opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(1),
                new ManaCostsImpl<>("{2}{B}")
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Hellbent - {1}, Pay 2 life: Draw a card. Activate this ability only if you have no cards in hand.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(1),
                HellbentCondition.instance
        );
        ability.addCost(new PayLifeCost(2));
        ability.setAbilityWord(AbilityWord.HELLBENT);
        this.addAbility(ability);
    }

    private NihilisticGlee(final NihilisticGlee card) {
        super(card);
    }

    @Override
    public NihilisticGlee copy() {
        return new NihilisticGlee(this);
    }
}
