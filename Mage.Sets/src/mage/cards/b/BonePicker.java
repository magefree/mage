package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author stravant
 */
public final class BonePicker extends CardImpl {

    public BonePicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bone Picker costs {3} less to cast if a creature died this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BonePickerAdjustingCostsEffect()).addHint(MorbidHint.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

    }

    private BonePicker(final BonePicker card) {
        super(card);
    }

    @Override
    public BonePicker copy() {
        return new BonePicker(this);
    }
}

class BonePickerAdjustingCostsEffect extends CostModificationEffectImpl {

    BonePickerAdjustingCostsEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {3} less to cast if a creature died this turn";
    }

    private BonePickerAdjustingCostsEffect(final BonePickerAdjustingCostsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId())
                && (abilityToModify instanceof SpellAbility)) {
            if (MorbidCondition.instance.apply(game, abilityToModify)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public BonePickerAdjustingCostsEffect copy() {
        return new BonePickerAdjustingCostsEffect(this);
    }
}
