
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class FlameBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new NamePredicate("Flame Burst"),
            new AbilityPredicate(CountAsFlameBurstAbility.class)));
    }

    public FlameBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Flame Burst deals X damage to any target, where X is 2 plus the number of cards named Flame Burst in all graveyards.
        Effect effect = new DamageTargetEffect(new FlameBurstCount(filter));
        effect.setText("{this} deals X damage to any target, where X is 2 plus the number of cards named Flame Burst in all graveyards.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private FlameBurst(final FlameBurst card) {
        super(card);
    }

    @Override
    public FlameBurst copy() {
        return new FlameBurst(this);
    }

    public static Ability getCountAsAbility() {
        return new CountAsFlameBurstAbility();
    }
}

class FlameBurstCount extends CardsInAllGraveyardsCount {

    public FlameBurstCount(FilterCard filter) {
        super(filter);
    }

    public FlameBurstCount(FlameBurstCount value) {
        super(value);
    }

    @Override
    public FlameBurstCount copy() {
        return new FlameBurstCount(this);
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        return super.calculate(game, source, effect) + 2;
    }

}

class CountAsFlameBurstAbility extends SimpleStaticAbility {

    public CountAsFlameBurstAbility() {
        super(Zone.GRAVEYARD, new InfoEffect("If {this} is in a graveyard, effects from spells named Flame Burst count it as a card named Flame Burst"));
    }

    private CountAsFlameBurstAbility(CountAsFlameBurstAbility ability) {
        super(ability);
    }

    @Override
    public CountAsFlameBurstAbility copy() {
        return new CountAsFlameBurstAbility(this);
    }
}
