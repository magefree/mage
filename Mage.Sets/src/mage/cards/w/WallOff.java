package mage.cards.w;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.permanent.token.WallColorlessToken;

/**
 *
 * @author muz
 */
public final class WallOff extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creature your opponents control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures your opponents control", xValue);

    public WallOff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // This spell costs {1} less to cast for each creature your opponents control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).setRuleAtTheTop(true).addHint(hint));

        // Create a 0/4 colorless Wall creature token with defender. You gain 4 life.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WallColorlessToken()));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private WallOff(final WallOff card) {
        super(card);
    }

    @Override
    public WallOff copy() {
        return new WallOff(this);
    }
}
