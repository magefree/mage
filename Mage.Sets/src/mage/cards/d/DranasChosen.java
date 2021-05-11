
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class DranasChosen extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");
    
    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public DranasChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Create a tapped 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken(), 1, true, false), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private DranasChosen(final DranasChosen card) {
        super(card);
    }

    @Override
    public DranasChosen copy() {
        return new DranasChosen(this);
    }
}
