
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpellBurst extends CardImpl {

    public SpellBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Counter target spell with converted mana cost X.
        this.getSpellAbility().addEffect(new CounterTargetEffect().setText("counter target spell with mana value X"));
        this.getSpellAbility().setTargetAdjuster(SpellBurstAdjuster.instance);
    }

    private SpellBurst(final SpellBurst card) {
        super(card);
    }

    @Override
    public SpellBurst copy() {
        return new SpellBurst(this);
    }
}

enum SpellBurstAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        ability.getTargets().clear();
        FilterSpell filter = new FilterSpell("spell with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetSpell(filter));
    }
}
