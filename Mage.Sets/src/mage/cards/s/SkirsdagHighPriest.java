
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.DemonToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class SkirsdagHighPriest extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SkirsdagHighPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // <i>Morbid</i> &mdash; {tap}, Tap two untapped creatures you control: Create a 5/5 black Demon creature token with flying. Activate this ability only if a creature died this turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DemonToken()),
            new TapSourceCost(), MorbidCondition.instance);
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false)));
        ability.setAbilityWord(AbilityWord.MORBID);
        this.addAbility(ability.addHint(MorbidHint.instance));
    }

    private SkirsdagHighPriest(final SkirsdagHighPriest card) {
        super(card);
    }

    @Override
    public SkirsdagHighPriest copy() {
        return new SkirsdagHighPriest(this);
    }
}
