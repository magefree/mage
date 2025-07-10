package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantPayLifeOrSacrificeAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class KarnsSylex extends CardImpl {
    public KarnsSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);

        // Karn’s Sylex
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Players can’t pay life to cast spells or to activate abilities that aren’t mana abilities.
        this.addAbility(new CantPayLifeOrSacrificeAbility(true, null));

        // {X}, {T}, Exile Karn’s Sylex: Destroy each nonland permanent with mana value X or less. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new KarnsSylexDestroyEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private KarnsSylex(final KarnsSylex card) {
        super(card);
    }

    @Override
    public KarnsSylex copy() {
        return new KarnsSylex(this);
    }
}

class KarnsSylexDestroyEffect extends OneShotEffect {

    KarnsSylexDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value X or less.";
    }

    private KarnsSylexDestroyEffect(final KarnsSylexDestroyEffect effect) {
        super(effect);
    }

    public KarnsSylexDestroyEffect copy() {
        return new KarnsSylexDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterNonlandPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, CardUtil.getSourceCostsTag(game, source, "X", 0) + 1));

        boolean destroyed = false;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            destroyed |= permanent.destroy(source, game);
        }
        return destroyed;
    }
}
