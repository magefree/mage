

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class StormtideLeviathan extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying or islandwalk");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    public StormtideLeviathan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Islandwalk (This creature can't be blocked as long as defending player controls an Island.)
        this.addAbility(new IslandwalkAbility());
        // All lands are Islands in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StormtideLeviathanEffect()));
        // Creatures without flying or islandwalk can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAnyPlayerAllEffect(Duration.WhileOnBattlefield, filter)));

    }

    public StormtideLeviathan(final StormtideLeviathan card) {
        super(card);
    }

    @Override
    public StormtideLeviathan copy() {
        return new StormtideLeviathan(this);
    }

}

class StormtideLeviathanEffect extends ContinuousEffectImpl {

    public StormtideLeviathanEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All lands are Islands in addition to their other types";
    }

    public StormtideLeviathanEffect(final StormtideLeviathanEffect effect) {
        super(effect);
    }

    @Override
    public StormtideLeviathanEffect copy() {
        return new StormtideLeviathanEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getActivePermanents(new FilterLandPermanent(), source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (!land.hasSubtype(SubType.ISLAND, game)) {
                        land.getSubtype(game).add(SubType.ISLAND);
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    boolean addAbility = true;
                    for (Ability existingAbility : land.getAbilities()) {
                        if (existingAbility instanceof BlueManaAbility) {
                            addAbility = false;
                            break;
                        }
                    }
                    if (addAbility) {
                        land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
