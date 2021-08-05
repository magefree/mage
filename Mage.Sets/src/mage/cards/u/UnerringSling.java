package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnerringSling extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("untapped creature you control");
    private static final FilterPermanent filter2
            = new FilterAttackingOrBlockingCreature("attacking or blocking creature with flying");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public UnerringSling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}, Tap an untapped creature you control: Unerring Sling deals damage equal to the tapped creature's power to target attacking or blocking creature with flying.
        Ability ability = new SimpleActivatedAbility(new UnerringSlingEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private UnerringSling(final UnerringSling card) {
        super(card);
    }

    @Override
    public UnerringSling copy() {
        return new UnerringSling(this);
    }
}

class UnerringSlingEffect extends OneShotEffect {

    UnerringSlingEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage equal to the tapped creature's power " +
                "to target attacking or blocking creature with flying";
    }

    private UnerringSlingEffect(final UnerringSlingEffect effect) {
        super(effect);
    }

    @Override
    public UnerringSlingEffect copy() {
        return new UnerringSlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        List<Permanent> permanents = (List<Permanent>) getValue("tappedPermanents");
        if (permanent == null
                || permanents == null
                || permanents.isEmpty()
                || permanents.get(0) == null) {
            return false;
        }
        return permanent.damage(permanents.get(0).getPower().getValue(), source.getSourceId(), source, game) > 0;
    }
}
