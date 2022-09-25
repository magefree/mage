package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TestamentOfFaith extends CardImpl {

    public TestamentOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // {X}: Testament of Faith becomes an X/X Wall creature with defender in addition to its other types until end of turn.
        Ability ability = new SimpleActivatedAbility(new SetBasePowerToughnessSourceEffect(
                ManacostVariableValue.REGULAR, Duration.EndOfTurn, SubLayer.SetPT_7b, true
        ).setText("{this} becomes an X/X"), new VariableManaCost(VariableCostType.NORMAL));
        ability.addEffect(new TestamentOfFaithEffect());
        ability.addEffect(new GainAbilitySourceEffect(
                DefenderAbility.getInstance(), Duration.EndOfTurn
        ).setText("with defender in addition to its other types until end of turn"));
        this.addAbility(ability);
    }

    private TestamentOfFaith(final TestamentOfFaith card) {
        super(card);
    }

    @Override
    public TestamentOfFaith copy() {
        return new TestamentOfFaith(this);
    }
}

class TestamentOfFaithEffect extends ContinuousEffectImpl {

    TestamentOfFaithEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "Wall creature";
    }

    private TestamentOfFaithEffect(final TestamentOfFaithEffect effect) {
        super(effect);
    }

    @Override
    public TestamentOfFaithEffect copy() {
        return new TestamentOfFaithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.addCardType(game, CardType.CREATURE);
        permanent.addSubType(game, SubType.WALL);
        return true;
    }
}
