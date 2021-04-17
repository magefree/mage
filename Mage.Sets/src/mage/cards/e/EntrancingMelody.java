
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntrancingMelody extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value X");

    public EntrancingMelody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Gain control of target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().setTargetAdjuster(EntrancingMelodyAdjuster.instance);
    }

    private EntrancingMelody(final EntrancingMelody card) {
        super(card);
    }

    @Override
    public EntrancingMelody copy() {
        return new EntrancingMelody(this);
    }
}

enum EntrancingMelodyAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCreaturePermanent(filter));
    }
}