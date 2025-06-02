package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class ChaliceOfLife extends CardImpl {

    public ChaliceOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.secondSideCardClazz = mage.cards.c.ChaliceOfDeath.class;
        this.addAbility(new TransformAbility());

        // {tap}: You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), MoreThanStartingLifeTotalCondition.TEN,
                "Then if you have at least 10 life more than your starting life total, transform {this}"
        ));
        this.addAbility(ability);
    }

    private ChaliceOfLife(final ChaliceOfLife card) {
        super(card);
    }

    @Override
    public ChaliceOfLife copy() {
        return new ChaliceOfLife(this);
    }
}
