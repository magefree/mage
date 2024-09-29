package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class Burnout extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant spell");

    static {
            filter.add(CardType.INSTANT.getPredicate());
    }

    public Burnout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Counter target instant spell if it's blue.
        Effect effect = new BurnoutCounterTargetEffect();
        effect.setText("Counter target instant spell if it's blue");
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(effect);

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false)
                .concatBy("<br>"));
    }

    private Burnout(final Burnout card) {
        super(card);
    }

    @Override
    public Burnout copy() {
        return new Burnout(this);
    }
}

class BurnoutCounterTargetEffect extends OneShotEffect {

    BurnoutCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    private BurnoutCounterTargetEffect(final BurnoutCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public BurnoutCounterTargetEffect copy() {
        return new BurnoutCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if(targetSpell != null && targetSpell.getColor(game).isBlue()){
            game.getStack().counter(source.getFirstTarget(), source, game);
        }
        return true;
    }
}
