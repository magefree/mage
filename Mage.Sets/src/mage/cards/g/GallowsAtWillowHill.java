
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class GallowsAtWillowHill extends CardImpl {

    private static final FilterControlledPermanent humanFilter = new FilterControlledPermanent("untapped Humans you control");

    static {
        humanFilter.add(TappedPredicate.UNTAPPED);
        humanFilter.add(SubType.HUMAN.getPredicate());
    }

    public GallowsAtWillowHill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap}, Tap three untapped Humans you control: Destroy target creature. Its controller creates a 1/1 white Spirit creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(3));
        ability.addEffect(new CreateTokenControllerTargetPermanentEffect(new SpiritWhiteToken()));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(3, 3, humanFilter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GallowsAtWillowHill(final GallowsAtWillowHill card) {
        super(card);
    }

    @Override
    public GallowsAtWillowHill copy() {
        return new GallowsAtWillowHill(this);
    }
}