package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleaverSkaab extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "another Zombie");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CleaverSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {3}, {T}, Sacrifice another Zombie: Create two tokens that are copies of the sacrificed creature.
        Ability ability = new SimpleActivatedAbility(new CleaverSkaabEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private CleaverSkaab(final CleaverSkaab card) {
        super(card);
    }

    @Override
    public CleaverSkaab copy() {
        return new CleaverSkaab(this);
    }
}

class CleaverSkaabEffect extends OneShotEffect {

    CleaverSkaabEffect() {
        super(Outcome.Benefit);
        staticText = "create two tokens that are copies of the sacrificed creature";
    }

    private CleaverSkaabEffect(final CleaverSkaabEffect effect) {
        super(effect);
    }

    @Override
    public CleaverSkaabEffect copy() {
        return new CleaverSkaabEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = CardUtil.castStream(
                source.getCosts().stream(), SacrificeTargetCost.class
        )
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setSavedPermanent(permanent);
        effect.setNumber(2);
        return effect.apply(game, source);
    }
}
