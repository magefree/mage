package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarRokuFirebender extends CardImpl {

    public AvatarRokuFirebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever a player attacks, add six {R}. Until end of combat, you don't lose this mana as steps end.
        this.addAbility(new AvatarRokuFirebenderTriggeredAbility());

        // {R}{R}{R}: Target creature gets +3/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(3, 0), new ManaCostsImpl<>("{R}{R}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AvatarRokuFirebender(final AvatarRokuFirebender card) {
        super(card);
    }

    @Override
    public AvatarRokuFirebender copy() {
        return new AvatarRokuFirebender(this);
    }
}

class AvatarRokuFirebenderTriggeredAbility extends TriggeredAbilityImpl {

    AvatarRokuFirebenderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AvatarRokuFirebenderEffect());
        setTriggerPhrase("Whenever a player attacks, ");
    }

    private AvatarRokuFirebenderTriggeredAbility(final AvatarRokuFirebenderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvatarRokuFirebenderTriggeredAbility copy() {
        return new AvatarRokuFirebenderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }
}

class AvatarRokuFirebenderEffect extends OneShotEffect {

    AvatarRokuFirebenderEffect() {
        super(Outcome.Benefit);
        staticText = "add six {R}. Until end of combat, you don't lose this mana as steps end";
    }

    private AvatarRokuFirebenderEffect(final AvatarRokuFirebenderEffect effect) {
        super(effect);
    }

    @Override
    public AvatarRokuFirebenderEffect copy() {
        return new AvatarRokuFirebenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getManaPool().addMana(Mana.RedMana(6), game, source, Duration.EndOfCombat);
        return true;
    }
}
