package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.VojaFriendToElvesToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TolsimirFriendToWolves extends CardImpl {

    public TolsimirFriendToWolves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Tolsimir, Friend to Wolves enters the battlefield, create Voja, Friend to Elves, a legendary 3/3 green and white Wolf creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new VojaFriendToElvesToken())));

        // Whenever a Wolf enters the battlefield under your control, you gain 3 life and that creature fights up to one target creature an opponent controls.
        this.addAbility(new TolsimirFriendToWolvesTriggeredAbility());
    }

    private TolsimirFriendToWolves(final TolsimirFriendToWolves card) {
        super(card);
    }

    @Override
    public TolsimirFriendToWolves copy() {
        return new TolsimirFriendToWolves(this);
    }
}

class TolsimirFriendToWolvesTriggeredAbility extends TriggeredAbilityImpl {

    TolsimirFriendToWolvesTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
    }

    private TolsimirFriendToWolvesTriggeredAbility(final TolsimirFriendToWolvesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isControlledBy(getControllerId())
                || !permanent.hasSubtype(SubType.WOLF, game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new TolsimirFriendToWolvesEffect(new MageObjectReference(permanent, game)));
        return true;
    }

    @Override
    public TolsimirFriendToWolvesTriggeredAbility copy() {
        return new TolsimirFriendToWolvesTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a Wolf enters the battlefield under your control, " +
                "you gain 3 life and that creature fights up to one target creature an opponent controls.";
    }

}

class TolsimirFriendToWolvesEffect extends OneShotEffect {

    private final MageObjectReference wolfMor;

    TolsimirFriendToWolvesEffect(MageObjectReference wolfMor) {
        super(Outcome.Benefit);
        this.wolfMor = wolfMor;
    }

    private TolsimirFriendToWolvesEffect(final TolsimirFriendToWolvesEffect effect) {
        super(effect);
        this.wolfMor = effect.wolfMor;
    }

    @Override
    public TolsimirFriendToWolvesEffect copy() {
        return new TolsimirFriendToWolvesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new GainLifeEffect(3).apply(game, source);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return true;
        }
        Permanent wolf = wolfMor.getPermanent(game);
        if (wolf == null) {
            return false;
        }
        return wolf.fight(permanent, source, game);
    }
}
