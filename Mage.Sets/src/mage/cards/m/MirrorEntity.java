package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class MirrorEntity extends CardImpl {

    public MirrorEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // {X}: Until end of turn, creatures you control have base power and toughness X/X and gain all creature types.
        Ability ability = new SimpleActivatedAbility(new SetBasePowerToughnessAllEffect(
                ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR,
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ).setText("Until end of turn, creatures you control have base power and toughness X/X"), new VariableManaCost(VariableCostType.NORMAL));
        ability.addEffect(new MirrorEntityEffect());
        this.addAbility(ability);
    }

    private MirrorEntity(final MirrorEntity card) {
        super(card);
    }

    @Override
    public MirrorEntity copy() {
        return new MirrorEntity(this);
    }
}

class MirrorEntityEffect extends ContinuousEffectImpl {

    MirrorEntityEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "and gain all creature types";
    }

    private MirrorEntityEffect(final MirrorEntityEffect effect) {
        super(effect);
    }

    @Override
    public MirrorEntityEffect copy() {
        return new MirrorEntityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURES, source.getControllerId(), source, game
        )) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent == null) {
                it.remove(); // no longer on the battlefield, remove reference to object
                continue;
            }
            permanent.setIsAllCreatureTypes(game, true);
        }
        return true;
    }
}
