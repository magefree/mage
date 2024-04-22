package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodfeatherPhoenix extends CardImpl {

    public BloodfeatherPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Bloodfeather Phoenix can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever an instant or sorcery spell you control deals damage to an opponent or battle, you may pay {R}. If you do, return Bloodfeather Phoenix from your graveyard to the battlefield. It gains haste until end of turn.
        this.addAbility(new BloodfeatherPhoenixTriggeredAbility());
    }

    private BloodfeatherPhoenix(final BloodfeatherPhoenix card) {
        super(card);
    }

    @Override
    public BloodfeatherPhoenix copy() {
        return new BloodfeatherPhoenix(this);
    }
}

class BloodfeatherPhoenixTriggeredAbility extends TriggeredAbilityImpl {

    BloodfeatherPhoenixTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new BloodfeatherPhoenixEffect(), new ManaCostsImpl<>("{R}")));
        setTriggerPhrase("Whenever an instant or sorcery spell you control deals damage to an opponent or battle, ");
    }

    private BloodfeatherPhoenixTriggeredAbility(final BloodfeatherPhoenixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodfeatherPhoenixTriggeredAbility copy() {
        return new BloodfeatherPhoenixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpellOrLKIStack(event.getSourceId());
        if (spell == null || !isControlledBy(spell.getControllerId()) || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        if (game.getOpponents(event.getTargetId()).contains(getControllerId())) {
            return true;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isBattle(game);
    }
}

class BloodfeatherPhoenixEffect extends OneShotEffect {

    BloodfeatherPhoenixEffect() {
        super(Outcome.Benefit);
        staticText = "return {this} from your graveyard to the battlefield. It gains haste until end of turn";
    }

    private BloodfeatherPhoenixEffect(final BloodfeatherPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public BloodfeatherPhoenixEffect copy() {
        return new BloodfeatherPhoenixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (player == null || sourceObject == null) {
            return false;
        }
        player.moveCards(game.getCard(sourceObject.getId()), Zone.BATTLEFIELD, source, game);
        if (game.getPermanent(source.getSourceId()) != null) {
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                    .setTargetPointer(new FixedTarget(source.getSourceId(), game)), source);
        }
        return true;
    }
}
