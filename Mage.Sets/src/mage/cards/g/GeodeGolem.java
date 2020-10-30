package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.ManaUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author spjspj, JayDi85
 */
public final class GeodeGolem extends CardImpl {

    public GeodeGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Geode Golem deals combat damage to a player, you may 
        // cast your commander from the command zone without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GeodeGolemEffect(), true));
    }

    public GeodeGolem(final GeodeGolem card) {
        super(card);
    }

    @Override
    public GeodeGolem copy() {
        return new GeodeGolem(this);
    }
}

class GeodeGolemEffect extends OneShotEffect {

    public GeodeGolemEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast your commander from the command zone "
                + "without paying its mana cost";
    }

    public GeodeGolemEffect(final GeodeGolemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID selectedCommanderId = null;
            Set<UUID> possibleCommanders = new HashSet<>();
            for (UUID id : game.getCommandersIds(controller)) {
                if (game.getState().getZone(id) == Zone.COMMAND) {
                    possibleCommanders.add(id);
                }
            }

            if (possibleCommanders.isEmpty()) {
                return false;
            }

            // select from commanders
            if (possibleCommanders.size() == 1) {
                selectedCommanderId = possibleCommanders.iterator().next();
            } else {
                TargetCard target = new TargetCard(Zone.COMMAND, new FilterCard(
                        "commander to cast without mana cost"));
                Cards cards = new CardsImpl(possibleCommanders);
                target.setNotTarget(true);
                if (controller.canRespond()
                        && controller.choose(Outcome.PlayForFree, cards, target, game)) {
                    if (target.getFirstTarget() != null) {
                        selectedCommanderId = target.getFirstTarget();
                    }
                }
            }

            Card commander = game.getCard(selectedCommanderId);
            if (commander == null) {
                return false;
            }

            // PAY
            // TODO: this is broken with the commander cost reduction effect
            ManaCost cost = null;
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            int castCount = watcher.getPlaysCount(commander.getId());
            if (castCount > 0) {
                cost = ManaUtil.createManaCost(castCount * 2, false);
            }

            // CAST: as spell or as land
            if (cost == null
                    || cost.pay(source, game, source.getSourceId(), controller.getId(), false, null)) {
                if (commander.getSpellAbility() != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + commander.getId(), Boolean.TRUE);
                    Boolean commanderWasCast = controller.cast(controller.chooseAbilityForCast(commander, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + commander.getId(), null);
                    return commanderWasCast;
                } else {
                    return controller.playLand(commander, game, true);
                }
            }
        }
        return false;
    }

    @Override
    public GeodeGolemEffect copy() {
        return new GeodeGolemEffect(this);
    }
}
