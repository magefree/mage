
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class GravespawnSovereign extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Zombies you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public GravespawnSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap five untapped Zombies you control: Put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(5, 5, filter, true)));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);

    }

    private GravespawnSovereign(final GravespawnSovereign card) {
        super(card);
    }

    @Override
    public GravespawnSovereign copy() {
        return new GravespawnSovereign(this);
    }
}
