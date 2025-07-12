package mage.cards.c;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CaptivatingVampire extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Vampire creatures");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("untapped Vampires you control");

    static {
        filter1.add(SubType.VAMPIRE.getPredicate());
        filter2.add(SubType.VAMPIRE.getPredicate());
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public CaptivatingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Vampire creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));

        // Tap five untapped Vampires you control: Gain control of target creature. It becomes a Vampire in addition to its other types.
        Ability ability = new SimpleActivatedAbility(new CaptivatingVampireEffect(), new TapTargetCost(new TargetControlledPermanent(5, 5, filter2, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CaptivatingVampire(final CaptivatingVampire card) {
        super(card);
    }

    @Override
    public CaptivatingVampire copy() {
        return new CaptivatingVampire(this);
    }

}

class CaptivatingVampireEffect extends ContinuousEffectImpl {

    CaptivatingVampireEffect() {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "Gain control of target creature. It becomes a Vampire in addition to its other types";
    }

    private CaptivatingVampireEffect(final CaptivatingVampireEffect effect) {
        super(effect);
    }

    @Override
    public CaptivatingVampireEffect copy() {
        return new CaptivatingVampireEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case ControlChangingEffects_2:
                    permanent.changeControllerId(source.getControllerId(), game, source);
                    break;
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.VAMPIRE);
                    break;
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            affectedObjects.add(permanent);
            return true;
        }
        discard();
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ControlChangingEffects_2 || layer == Layer.TypeChangingEffects_4;
    }

}
