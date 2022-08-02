
package mage.cards.j;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author MarcoMarin
 */
public final class Jihad extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public Jihad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{W}{W}");

        // As Jihad enters the battlefield, choose a color and an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        
        // White creatures get +2/+1 as long as the chosen player controls a nontoken permanent of the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostAllEffect(2, 1, Duration.WhileOnBattlefield, filter, false), new JihadOpponentCondition(), "White creatures get +2/+1 as long as the chosen player controls a nontoken permanent of the chosen color.")));
        
        // When the chosen player controls no nontoken permanents of the chosen color, sacrifice Jihad.
        this.addAbility(new JihadTriggeredAbility(new SacrificeSourceEffect()));
    }

    private Jihad(final Jihad card) {
        super(card);
    }

    @Override
    public Jihad copy() {
        return new Jihad(this);
    }
}

class JihadTriggeredAbility extends StateTriggeredAbility {

    public JihadTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("When the chosen player controls no nontoken permanents of the chosen color, ");
    }

    public JihadTriggeredAbility(final JihadTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JihadTriggeredAbility copy() {
        return new JihadTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID chosenOpponent = (UUID) game.getState().getValue(getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(getSourceId() + "_color")));
        filter.add(TokenPredicate.FALSE);
        return game.getBattlefield().countAll(filter, chosenOpponent, game) == 0;
    }
}

class JihadOpponentCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        UUID chosenOpponent = (UUID) game.getState().getValue(source.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY);
        FilterPermanent filter = new FilterPermanent();
        filter.add(new ColorPredicate((ObjectColor) game.getState().getValue(source.getSourceId() + "_color")));
        filter.add(TokenPredicate.FALSE);
        return game.getBattlefield().countAll(filter, chosenOpponent, game) > 0;
    }
}