
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SpawnbinderMage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Ally you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SpawnbinderMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // <i>Cohort</i> &mdash; {T}, Tap an untapped Ally you control: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setAbilityWord(AbilityWord.COHORT);
        this.addAbility(ability);
    }

    private SpawnbinderMage(final SpawnbinderMage card) {
        super(card);
    }

    @Override
    public SpawnbinderMage copy() {
        return new SpawnbinderMage(this);
    }
}
