
package mage.cards.n;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NightDay extends SplitCard {

    public NightDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}", "{2}{W}", SpellAbilityType.SPLIT);

        // Night
        // Target creature gets -1/-1 until end of turn.
        getLeftHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Day
        // Creatures target player controls get +1/+1 until end of turn.
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
        getRightHalfCard().getSpellAbility().addEffect(new DayEffect());

    }

    private NightDay(final NightDay card) {
        super(card);
    }

    @Override
    public NightDay copy() {
        return new NightDay(this);
    }
}

class DayEffect extends ContinuousEffectImpl {

    public DayEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures target player controls get +1/+1 until end of turn";
    }

    private DayEffect(final DayEffect effect) {
        super(effect);
    }

    @Override
    public DayEffect copy() {
        return new DayEffect(this);
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
                permanent.addPower(1);
                permanent.addToughness(1);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
