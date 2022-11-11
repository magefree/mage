package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */

public final class SkyboonEvangelist extends CardImpl {

    public SkyboonEvangelist(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Skyboon Evangelist enters the battlefield, support 6.
        this.addAbility(new SupportAbility(this, 6));

        // Whenever a creature with a counter on it attacks one of your opponents, that creature gains flying until end of turn.
        this.addAbility(new SkyboonEvangelistTriggeredAbility());
    }

    private SkyboonEvangelist(final SkyboonEvangelist card) {
        super(card);
    }

    @Override
    public SkyboonEvangelist copy() {
        return new SkyboonEvangelist(this);
    }
}

class SkyboonEvangelistTriggeredAbility extends AttacksAllTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    SkyboonEvangelistTriggeredAbility() {
        super(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(),
                Duration.EndOfTurn
        ).setText("that creature gains flying until end of turn"), false, filter, SetTargetPointer.PERMANENT, false);
    }

    SkyboonEvangelistTriggeredAbility(final SkyboonEvangelistTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event,game)) {
            Player defender = game.getPlayer(event.getTargetId());
            if (defender == null) {
                return false;
            }
            Set<UUID> opponents = game.getOpponents(this.getControllerId());
            if (opponents != null && opponents.contains(defender.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with a counter on it attacks one of your opponents, that creature gains flying until end of turn.";
    }

    @Override
    public SkyboonEvangelistTriggeredAbility copy() {
        return new SkyboonEvangelistTriggeredAbility(this);
    }
}