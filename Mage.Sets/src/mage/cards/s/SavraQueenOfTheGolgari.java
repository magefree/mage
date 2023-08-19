
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SavraQueenOfTheGolgari extends CardImpl {

    public SavraQueenOfTheGolgari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice a black creature, you may pay 2 life. If you do, each other player sacrifices a creature.
        this.addAbility(new SavraSacrificeBlackCreatureAbility());

        // Whenever you sacrifice a green creature, you may gain 2 life.
        this.addAbility(new SavraSacrificeGreenCreatureAbility());
    }

    private SavraQueenOfTheGolgari(final SavraQueenOfTheGolgari card) {
        super(card);
    }

    @Override
    public SavraQueenOfTheGolgari copy() {
        return new SavraQueenOfTheGolgari(this);
    }
}

class SavraSacrificeBlackCreatureAbility extends TriggeredAbilityImpl {

    public SavraSacrificeBlackCreatureAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new SavraSacrificeEffect(), new PayLifeCost(2)));
        this.setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever you sacrifice a black creature, ");
    }

    public SavraSacrificeBlackCreatureAbility(final SavraSacrificeBlackCreatureAbility ability) {
        super(ability);
    }

    @Override
    public SavraSacrificeBlackCreatureAbility copy() {
        return new SavraSacrificeBlackCreatureAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game)
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).getColor(game).isBlack();
    }
}

class SavraSacrificeEffect extends OneShotEffect {

    public SavraSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature";
    }

    public SavraSacrificeEffect(final SavraSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SavraSacrificeEffect copy() {
        return new SavraSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class SavraSacrificeGreenCreatureAbility extends TriggeredAbilityImpl {

    public SavraSacrificeGreenCreatureAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2), true);
        setTriggerPhrase("Whenever you sacrifice a green creature, ");
    }

    public SavraSacrificeGreenCreatureAbility(final SavraSacrificeGreenCreatureAbility ability) {
        super(ability);
    }

    @Override
    public SavraSacrificeGreenCreatureAbility copy() {
        return new SavraSacrificeGreenCreatureAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game)
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).getColor(game).isGreen();
    }
}
