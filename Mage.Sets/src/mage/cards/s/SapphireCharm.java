
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SapphireCharm extends CardImpl {

    public SapphireCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - Target player draws a card at the beginning of the next turn's upkeep;
        Effect effect = new DrawCardTargetEffect(1);
        effect.setText("Target player draws a card");
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(effect), true));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // or target creature gains flying until end of turn;
        Mode mode = new Mode(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        
        // or target creature an opponent controls phases out.
        mode = new Mode(new PhaseOutTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.getSpellAbility().addMode(mode);
    }

    private SapphireCharm(final SapphireCharm card) {
        super(card);
    }

    @Override
    public SapphireCharm copy() {
        return new SapphireCharm(this);
    }
}
