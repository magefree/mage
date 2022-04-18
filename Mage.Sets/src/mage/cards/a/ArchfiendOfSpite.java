package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchfiendOfSpite extends CardImpl {

    public ArchfiendOfSpite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source an opponent controls deals damage to Archfiend of Spite, that source's controller loses that much life unless they sacrifice that many permanents.
        this.addAbility(new ArchfiendOfSpiteAbility());

        // Madness {3}{B}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{3}{B}{B}")));

    }

    private ArchfiendOfSpite(final ArchfiendOfSpite card) {
        super(card);
    }

    @Override
    public ArchfiendOfSpite copy() {
        return new ArchfiendOfSpite(this);
    }
}

class ArchfiendOfSpiteAbility extends TriggeredAbilityImpl {

    ArchfiendOfSpiteAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private ArchfiendOfSpiteAbility(final ArchfiendOfSpiteAbility ability) {
        super(ability);
    }

    @Override
    public ArchfiendOfSpiteAbility copy() {
        return new ArchfiendOfSpiteAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        UUID sourceControllerId = game.getControllerId(event.getSourceId());
        if (sourceControllerId == null ||
                !game.getOpponents(getControllerId()).contains(sourceControllerId)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new ArchfiendOfSpiteEffect(event.getAmount(), sourceControllerId));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to {this}, " +
                "that source's controller loses that much life unless they sacrifice that many permanents.";
    }
}

class ArchfiendOfSpiteEffect extends OneShotEffect {

    private final UUID playerId;
    private final int amount;
    private final Effect effect;

    ArchfiendOfSpiteEffect(int amount, UUID playerId) {
        super(Outcome.Benefit);
        this.playerId = playerId;
        this.amount = amount;
        this.effect = new SacrificeEffect(StaticFilters.FILTER_PERMANENTS, amount, "");
        this.effect.setTargetPointer(new FixedTarget(playerId));
    }

    private ArchfiendOfSpiteEffect(final ArchfiendOfSpiteEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.amount = effect.amount;
        this.effect = effect.effect.copy();
    }

    @Override
    public ArchfiendOfSpiteEffect copy() {
        return new ArchfiendOfSpiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return false;
        }
        if (game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT, playerId, game).size() < amount
                || player.chooseUse(outcome, "Lose " + amount + " life or sacrifice " + amount + " permanents?",
                null, "Lose " + amount + " life",
                "Sacrifice " + amount + " permanents", source, game
        )) {
            return player.loseLife(amount, game, source, false) > 0;
        }
        return effect.apply(game, source);
    }
}
