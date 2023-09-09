
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CaptivatingVampire extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Vampire creatures");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("untapped Vampires you control");

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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));

        // Tap five untapped Vampires you control: Gain control of target creature. It becomes a Vampire in addition to its other types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CaptivatingVampireEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(5, 5, filter2, true)));
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

    public CaptivatingVampireEffect() {
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
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            switch (layer) {
                case ControlChangingEffects_2:
                    if (sublayer == SubLayer.NA) {
                        permanent.changeControllerId(source.getControllerId(), game, source);
                    }
                    break;
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.VAMPIRE);
                    break;
            }
            return true;
        }
        discard();
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ControlChangingEffects_2 || layer == Layer.TypeChangingEffects_4;
    }

}
