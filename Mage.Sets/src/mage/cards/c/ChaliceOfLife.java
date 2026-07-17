package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class ChaliceOfLife extends TransformingDoubleFacedCard {

    public ChaliceOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{3}",
                "Chalice of Death",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "");

        // Chalice of Life
        // {T}: You gain 1 life. Then if you have at least 10 life more than your starting life total, transform Chalice of Life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), MoreThanStartingLifeTotalCondition.TEN,
                "Then if you have at least 10 life more than your starting life total, transform {this}"
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Chalice of Death
        // {T}: Target player loses 5 life.
        Ability deathAbility = new SimpleActivatedAbility(new LoseLifeTargetEffect(5), new TapSourceCost());
        deathAbility.addTarget(new TargetPlayer());
        this.getRightHalfCard().addAbility(deathAbility);
    }

    private ChaliceOfLife(final ChaliceOfLife card) {
        super(card);
    }

    @Override
    public ChaliceOfLife copy() {
        return new ChaliceOfLife(this);
    }
}
