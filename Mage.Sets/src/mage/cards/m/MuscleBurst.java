
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class MuscleBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new NamePredicate("Muscle Burst"),
            new AbilityPredicate(CountAsMuscleBurstAbility.class)));
    }

    public MuscleBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target creature gets +X/+X until end of turn, where X is 3 plus the number of cards named Muscle Burst in all graveyards.
        MuscleBurstCount count = new MuscleBurstCount(filter);
        Effect effect = new BoostTargetEffect(count, count, Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+X until end of turn, where X is 3 plus the number of cards named Muscle Burst in all graveyards.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MuscleBurst(final MuscleBurst card) {
        super(card);
    }

    @Override
    public MuscleBurst copy() {
        return new MuscleBurst(this);
    }

    public static Ability getCountAsAbility() {
        return new CountAsMuscleBurstAbility();
    }
}

class MuscleBurstCount extends CardsInAllGraveyardsCount {

    public MuscleBurstCount(FilterCard filter) {
        super(filter);
    }

    private MuscleBurstCount(MuscleBurstCount value) {
        super(value);
    }

    @Override
    public MuscleBurstCount copy() {
        return new MuscleBurstCount(this);
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        return super.calculate(game, source, effect) + 3;
    }

}

class CountAsMuscleBurstAbility extends SimpleStaticAbility {

    public CountAsMuscleBurstAbility() {
        super(Zone.GRAVEYARD, new InfoEffect("If {this} is in a graveyard, effects from spells named Muscle Burst count it as a card named Muscle Burst"));
    }

    private CountAsMuscleBurstAbility(CountAsMuscleBurstAbility ability) {
        super(ability);
    }

    @Override
    public CountAsMuscleBurstAbility copy() {
        return new CountAsMuscleBurstAbility(this);
    }
}
