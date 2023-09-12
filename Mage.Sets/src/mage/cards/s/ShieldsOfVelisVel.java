
package mage.cards.s;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class ShieldsOfVelisVel extends CardImpl {

    public ShieldsOfVelisVel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        //Creatures target player controls get +0/+1 and gain all creature types until end of turn.
        this.getSpellAbility().addEffect(new ShieldsOfVelisVelBoostEffect());
        this.getSpellAbility().addEffect(new ShieldsOfVelisVelGainEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private ShieldsOfVelisVel(final ShieldsOfVelisVel card) {
        super(card);
    }

    @Override
    public ShieldsOfVelisVel copy() {
        return new ShieldsOfVelisVel(this);
    }
}

class ShieldsOfVelisVelGainEffect extends ContinuousEffectImpl {

    public ShieldsOfVelisVelGainEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "and gain all creature types until end of turn";
    }

    private ShieldsOfVelisVelGainEffect(final ShieldsOfVelisVelGainEffect effect) {
        super(effect);
    }

    @Override
    public ShieldsOfVelisVelGainEffect copy() {
        return new ShieldsOfVelisVelGainEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.setIsAllCreatureTypes(game, true);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

class ShieldsOfVelisVelBoostEffect extends ContinuousEffectImpl {

    public ShieldsOfVelisVelBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures target player controls get +0/+1";
    }

    private ShieldsOfVelisVelBoostEffect(final ShieldsOfVelisVelBoostEffect effect) {
        super(effect);
    }

    @Override
    public ShieldsOfVelisVelBoostEffect copy() {
        return new ShieldsOfVelisVelBoostEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addToughness(1);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
