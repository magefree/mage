package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Objects;
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
        this.addAbility(new SimpleStaticAbility(new KarnsSylexEffect()));

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

class KarnsSylexEffect extends ContinuousEffectImpl {

    public KarnsSylexEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "Players can't pay life to cast spells or to activate abilities that aren't mana abilities";
    }

    public KarnsSylexEffect(final KarnsSylexEffect effect) {
        super(effect);
    }

    @Override
    public KarnsSylexEffect copy() {
        return new KarnsSylexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            player.setPayLifeCostLevel(Player.PayLifeCostLevel.onlyManaAbilities);
        }
        return true;
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
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));

        boolean destroyed = false;
        for (Permanent permanent : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            destroyed |= permanent.destroy(source, game);
        }
        return destroyed;
    }
}
