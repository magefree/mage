
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MalakirSoothsayer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Ally you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MalakirSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: You draw a card and you lose a life.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost());
        ability.setAbilityWord(AbilityWord.COHORT);
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        Effect effect = new LoseLifeSourceControllerEffect(1);
        effect.setText("and you lose a life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public MalakirSoothsayer(final MalakirSoothsayer card) {
        super(card);
    }

    @Override
    public MalakirSoothsayer copy() {
        return new MalakirSoothsayer(this);
    }
}
