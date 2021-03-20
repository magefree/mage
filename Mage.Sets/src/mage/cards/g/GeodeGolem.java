package mage.cards.g;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

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

        // Whenever Geode Golem deals combat damage to a player, you may cast your commander from the command zone without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GeodeGolemEffect(), true));
    }

    private GeodeGolem(final GeodeGolem card) {
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
            Card selectedCommander = null;

            Set<Card> commandersInCommandZone = game.getCommanderCardsFromCommandZone(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER);
            if (commandersInCommandZone.isEmpty()) {
                return false;
            }

            // select from commanders
            if (commandersInCommandZone.size() == 1) {
                selectedCommander = commandersInCommandZone.stream().findFirst().get();
            } else {
                TargetCard target = new TargetCard(Zone.COMMAND, new FilterCard("commander to cast without mana cost"));
                target.setNotTarget(true);
                if (controller.canRespond()
                        && controller.choose(Outcome.PlayForFree, new CardsImpl(commandersInCommandZone), target, game)) {
                    selectedCommander = commandersInCommandZone.stream()
                            .filter(c -> c.getId().equals(target.getFirstTarget()))
                            .findFirst()
                            .orElse(null);

                }
            }

            if (selectedCommander == null) {
                return false;
            }

            // commander tax applies as additional cost
            if (selectedCommander.getSpellAbility() != null) {
                game.getState().setValue("PlayFromNotOwnHandZone" + selectedCommander.getId(), Boolean.TRUE);
                Boolean commanderWasCast = controller.cast(controller.chooseAbilityForCast(selectedCommander, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + selectedCommander.getId(), null);
                return commanderWasCast;
            } else {
                // play commander as land is xmage feature, but mtg rules for text "cast commander" doesn't allow that
                // TODO: improve lands support for "cast your commander" (allow land play from mdf cards)?
                return controller.playLand(selectedCommander, game, true);
            }
        }
        return false;
    }

    @Override
    public GeodeGolemEffect copy() {
        return new GeodeGolemEffect(this);
    }
}
