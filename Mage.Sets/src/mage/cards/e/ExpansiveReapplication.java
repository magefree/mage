package mage.cards.e;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.UUID;

public final class ExpansiveReapplication extends CardImpl {

    public ExpansiveReapplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{U}");

        // X can't be 0.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("X can't be 0")));
        CardUtil.castStream(this.getSpellAbility().getManaCosts(), VariableManaCost.class)
                .forEach(cost -> cost.setMinX(1));

        // Counter target spell. Conjure a random creature card with mana value X onto the battlefield.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ConjureRandomCreatureEffect(GetXValue.instance));
    }

    private ExpansiveReapplication(final ExpansiveReapplication card) {
        super(card);
    }

    @Override
    public ExpansiveReapplication copy() {
        return new ExpansiveReapplication(this);
    }
}
