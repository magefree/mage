
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Armistice extends CardImpl {

    public Armistice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // {3}{W}{W}: You draw a card and target opponent gains 3 life.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1, true), new ManaCostsImpl<>("{3}{W}{W}"));
        ability.addEffect(new GainLifeTargetEffect(3).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Armistice(final Armistice card) {
        super(card);
    }

    @Override
    public Armistice copy() {
        return new Armistice(this);
    }
}
