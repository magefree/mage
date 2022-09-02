
package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class SuddenSpoiling extends CardImpl {

    public SuddenSpoiling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Until end of turn, creatures target player controls lose all abilities and have base power and toughness 0/2.
        this.getSpellAbility().addEffect(new SuddenSpoilingEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private SuddenSpoiling(final SuddenSpoiling card) {
        super(card);
    }

    @Override
    public SuddenSpoiling copy() {
        return new SuddenSpoiling(this);
    }
}

class SuddenSpoilingEffect extends ContinuousEffectImpl {

    public SuddenSpoilingEffect(Duration duration) {
        super(duration, Outcome.LoseAbility);
        staticText = "Until end of turn, creatures target player controls lose all abilities and have base power and toughness 0/2";
    }

    public SuddenSpoilingEffect(final SuddenSpoilingEffect effect) {
        super(effect);
    }

    @Override
    public SuddenSpoilingEffect copy() {
        return new SuddenSpoilingEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return;
        }
        for (Permanent perm : game.getState().getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
            if (affectedObjectList.contains(new MageObjectReference(permanent, game))) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setModifiedBaseValue(0);
                            permanent.getToughness().setModifiedBaseValue(2);
                        }
                }
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
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.PTChangingEffects_7;
    }

}
