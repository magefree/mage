
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Armistice extends CardImpl {

    public Armistice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");


        // {3}{W}{W}: You draw a card and target opponent gains 3 life.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("You draw a card");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}{W}{W}"));
        effect = new GainLifeTargetEffect(3);
        effect.setText("and target opponent gains 3 life");
        ability.addEffect(effect);
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
