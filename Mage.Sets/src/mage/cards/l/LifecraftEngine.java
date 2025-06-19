package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author jackd149
 */
public final class LifecraftEngine extends CardImpl {

    public LifecraftEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As this Vehicle enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Vehicle creatures you control are the chosen creature type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new LifecraftEngineAddSubTypeAllEffect()));

        // Each creature you control of the chosen type other than this Vehicle gets +1/+1.
        BoostAllOfChosenSubtypeEffect effect = new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, true);
        effect.setText("Each creature you control of the chosen type other than this Vehicle gets +1/+1.");
        this.addAbility(new SimpleStaticAbility(effect));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private LifecraftEngine(final LifecraftEngine card) {
        super(card);
    }

    @Override
    public LifecraftEngine copy() {
        return new LifecraftEngine(this);
    }
}

class LifecraftEngineAddSubTypeAllEffect extends ContinuousEffectImpl {
    
    static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SubType.VEHICLE.getPredicate());
    }

    public LifecraftEngineAddSubTypeAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);

        staticText = "Vehicle creatures you control are the chosen creature type in addition to their other types.";
    }

    private LifecraftEngineAddSubTypeAllEffect(final LifecraftEngineAddSubTypeAllEffect effect) {
        super(effect);
    }

    @Override
    public LifecraftEngineAddSubTypeAllEffect copy() {
        return new LifecraftEngineAddSubTypeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Player controller = game.getPlayer(controllerId);
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (controller == null || subType == null) {
            return false;
        }

        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                filter, controllerId, game);
        for (Permanent creature : creatures) {
            if (creature != null) {
                creature.addSubType(game, subType);
            }
        }
        return true;
    }
}
