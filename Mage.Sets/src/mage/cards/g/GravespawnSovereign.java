package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class GravespawnSovereign extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("untapped Zombies you control");

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
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), new TapTargetCost(5, filter)
        );
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
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
