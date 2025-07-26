package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ChariotOfTheSun extends CardImpl {

    public ChariotOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {tap}: Until end of turn, target creature you control gains flying and its toughness becomes 1.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(
                        FlyingAbility.getInstance(),
                        Duration.EndOfTurn,
                        "until end of turn, target creature you control gains flying"
                ),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new ChariotOfTheSunEffect());
        this.addAbility(ability);
    }

    private ChariotOfTheSun(final ChariotOfTheSun card) {
        super(card);
    }

    @Override
    public ChariotOfTheSun copy() {
        return new ChariotOfTheSun(this);
    }
}

class ChariotOfTheSunEffect extends ContinuousEffectImpl {

    ChariotOfTheSunEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.UnboostCreature);
        staticText = "and has base toughness 1";
    }

    private ChariotOfTheSunEffect(final ChariotOfTheSunEffect effect) {
        super(effect);
    }

    @Override
    public ChariotOfTheSunEffect copy() {
        return new ChariotOfTheSunEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.getToughness().setModifiedBaseValue(1);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            this.discard();
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }
}
