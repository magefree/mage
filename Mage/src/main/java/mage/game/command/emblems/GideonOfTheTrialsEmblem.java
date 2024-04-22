package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;

/**
 * @author spjspj
 */
public final class GideonOfTheTrialsEmblem extends Emblem {

    public GideonOfTheTrialsEmblem() {
        super("Emblem Gideon");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new GideonOfTheTrialsCantLoseEffect());
        this.getAbilities().add(ability);
    }

    private GideonOfTheTrialsEmblem(final GideonOfTheTrialsEmblem card) {
        super(card);
    }

    @Override
    public GideonOfTheTrialsEmblem copy() {
        return new GideonOfTheTrialsEmblem(this);
    }
}

class GideonOfTheTrialsCantLoseEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a Gideon planeswalker");

    static {
        filter.add(SubType.GIDEON.getPredicate());
    }

    public GideonOfTheTrialsCantLoseEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, false, false);
        staticText = "As long as you control a Gideon planeswalker, you can't lose the game and your opponents can't win the game";
    }

    protected GideonOfTheTrialsCantLoseEffect(final GideonOfTheTrialsCantLoseEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.WINS
                || event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.WINS && game.getOpponents(source.getControllerId()).contains(event.getPlayerId()))
                || (event.getType() == GameEvent.EventType.LOSES && event.getPlayerId().equals(source.getControllerId()))) {
            return game.getBattlefield().containsControlled(filter, source, game, 1);
        }
        return false;
    }

    @Override
    public GideonOfTheTrialsCantLoseEffect copy() {
        return new GideonOfTheTrialsCantLoseEffect(this);
    }
}
