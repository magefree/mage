package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
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
        staticText = "you may cast your commander from the command zone without paying its mana cost";
    }

    public GeodeGolemEffect(final GeodeGolemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID commanderId : controller.getCommandersIds()) {
                if (game.getState().getZone(commanderId) == Zone.COMMAND) {
                    Card commander = game.getCard(commanderId);

                    if (commander != null && game.getState().getZone(commanderId) == Zone.COMMAND) {
                        SpellAbility ability = commander.getSpellAbility();
                        SpellAbility newAbility = commander.getSpellAbility().copy();
                        newAbility.getCosts().clear();

                        Integer castCount = (Integer) game.getState().getValue(commander.getId() + "_castCount");
                        Cost cost = null;
                        if (castCount > 0) {
                            cost = new GenericManaCost(castCount * 2);
                        }

                        if ((castCount == 0 || castCount > 0 && cost.pay(source, game, source.getSourceId(), controller.getId(), false, null))
                                && controller.cast(newAbility, game, true, new MageObjectReference(source.getSourceObject(game), game))) {
                            // save amount of times commander was cast
                            if (castCount == null) {
                                castCount = 1;
                            } else {
                                castCount++;
                            }
                            game.getState().setValue(commander.getId() + "_castCount", castCount);
                            return true;
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public GeodeGolemEffect copy() {
        return new GeodeGolemEffect(this);
    }
}
