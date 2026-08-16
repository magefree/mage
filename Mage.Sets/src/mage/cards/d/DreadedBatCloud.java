package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DreadedBatCloud extends CardImpl {

    public DreadedBatCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.BAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // This spell costs {3} less to cast if a creature died this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new DreadedBatCloudAdjustingCostsEffect()).addHint(MorbidHint.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private DreadedBatCloud(final DreadedBatCloud card) {
        super(card);
    }

    @Override
    public DreadedBatCloud copy() {
        return new DreadedBatCloud(this);
    }
}

class DreadedBatCloudAdjustingCostsEffect extends CostModificationEffectImpl {

    DreadedBatCloudAdjustingCostsEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "this spell costs {3} less to cast if a creature died this turn";
    }

    private DreadedBatCloudAdjustingCostsEffect(final DreadedBatCloudAdjustingCostsEffect effect) {
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
    public DreadedBatCloudAdjustingCostsEffect copy() {
        return new DreadedBatCloudAdjustingCostsEffect(this);
    }
}
