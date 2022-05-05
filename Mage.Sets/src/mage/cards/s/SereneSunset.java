package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SereneSunset extends CardImpl {

    public SereneSunset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Prevent all combat damage X target creatures would deal this turn.
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true)
                .setText("prevent all combat damage X target creatures would deal this turn"));
        this.getSpellAbility().setTargetAdjuster(SereneSunsetAdjuster.instance);
    }

    private SereneSunset(final SereneSunset card) {
        super(card);
    }

    @Override
    public SereneSunset copy() {
        return new SereneSunset(this);
    }
}

enum SereneSunsetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}
