package mage.cards.b;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class BannerOfKinship extends CardImpl {

    public BannerOfKinship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As this artifact enters, choose a creature type. This artifact enters with a fellowship counter on it for each creature you control of the chosen type.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature));
        ability.addEffect(new BannerOfKinshipEffect());
        this.addAbility(ability);

        // Creatures you control of the chosen type get +1/+1 for each fellowship counter on this artifact.
        this.addAbility(new SimpleStaticAbility(new BannerOfKinshipBoostEffect()));
    }

    private BannerOfKinship(final BannerOfKinship card) {
        super(card);
    }

    @Override
    public BannerOfKinship copy() {
        return new BannerOfKinship(this);
    }
}

class BannerOfKinshipEffect extends OneShotEffect {

    BannerOfKinshipEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "This artifact enters with a fellowship counter on it for each creature you control of the chosen type";
    }

    private BannerOfKinshipEffect(final BannerOfKinshipEffect effect) {
        super(effect);
    }

    @Override
    public BannerOfKinshipEffect copy() {
        return new BannerOfKinshipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
        int amount = game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(subtype), source.getControllerId(), game).size();
        if (amount > 0) {
            permanent.addCounters(CounterType.FELLOWSHIP.createInstance(amount), source.getControllerId(), source, game);
        }
        return true;
    }
}

class BannerOfKinshipBoostEffect extends ContinuousEffectImpl {

    BannerOfKinshipBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen type get +1/+1 for each fellowship counter on this artifact";
    }

    private BannerOfKinshipBoostEffect(final BannerOfKinshipBoostEffect effect) {
        super(effect);
    }

    @Override
    public BannerOfKinshipBoostEffect copy() {
        return new BannerOfKinshipBoostEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        int boost = game.getPermanent(source.getSourceId())
                .getCounters(game)
                .getCount(CounterType.FELLOWSHIP);
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(boost);
            permanent.addToughness(boost);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
        if (subtype == null) {
            return false;
        }
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(subtype);
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        return !affectedObjects.isEmpty();
    }
}
