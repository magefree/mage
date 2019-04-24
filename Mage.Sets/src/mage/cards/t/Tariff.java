
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public final class Tariff extends CardImpl {

    public Tariff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");


        // Each player sacrifices the creature he or she controls with the highest converted mana cost unless he or she pays that creature's mana cost. If two or more creatures a player controls are tied for highest cost, that player chooses one.
        this.getSpellAbility().addEffect(new TariffEffect());
    }

    public Tariff(final Tariff card) {
        super(card);
    }

    @Override
    public Tariff copy() {
        return new Tariff(this);
    }
}

class TariffEffect extends OneShotEffect {
    
    public TariffEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player sacrifices the creature he or she controls with the highest converted mana cost unless he or she pays that creature's mana cost. If two or more creatures a player controls are tied for highest cost, that player chooses one.";
    }
    
    public TariffEffect(final TariffEffect effect) {
        super(effect);
    }

    @Override
    public TariffEffect copy() {
        return new TariffEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        PlayerList playerList = game.getPlayerList().copy();
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        
        do {
            processPlayer(game, source, player);
            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));
        
        return true;
    }
    
    private void processPlayer(Game game, Ability source, Player player) {
        MageObject sourceObject = game.getObject(source.getSourceId());

        List<Permanent> creatures = getPermanentsWithTheHighestCMC(game, player.getId(), new FilterControlledCreaturePermanent());

        Permanent creatureToPayFor = chooseOnePermanent(game, player, creatures);

        if (creatureToPayFor != null) {
            ManaCosts manaCost = ManaCosts.removeVariableManaCost(creatureToPayFor.getManaCost());
            String message = new StringBuilder("Pay ").append(manaCost.getText()).append(" (otherwise sacrifice ")
                    .append(creatureToPayFor.getName()).append(")?").toString();
            if (player.chooseUse(Outcome.Benefit, message, source, game)) {
                if (manaCost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                    game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getName() : "")
                            .append(": ").append(player.getLogName()).append(" has paid").toString());
                    return;
                }
            }

            game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getName() : "")
                    .append(": ").append(player.getLogName()).append(" hasn't paid").toString());
            creatureToPayFor.sacrifice(source.getSourceId(), game);
        }
    }
    
    private List<Permanent> getPermanentsWithTheHighestCMC(Game game, UUID playerId, FilterPermanent filter) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, playerId, game);
        int highestCMC = -1;
        for (Permanent permanent : permanents) {
            if (highestCMC < permanent.getConvertedManaCost()) {
                highestCMC = permanent.getConvertedManaCost();
            }
        }
        List<Permanent> result = new ArrayList<>();
        for (Permanent permanent : permanents) {
            if (permanent.getConvertedManaCost() == highestCMC) {
                result.add(permanent);
            }
        }
        return result;
    }

    private Permanent chooseOnePermanent(Game game, Player player, List<Permanent> permanents) {
        Permanent permanent = null;
        if (permanents.size() == 1) {
            permanent = permanents.iterator().next();
        } else if (permanents.size() > 1) {
            Cards cards = new CardsImpl();
            for (Permanent card : permanents) {
                cards.add(card);
            }

            TargetCard targetCard = new TargetCard(Zone.BATTLEFIELD, new FilterCard());
            if (player.choose(Outcome.Benefit, cards, targetCard, game)) {
                permanent = game.getPermanent(targetCard.getFirstTarget());
            }
        }
        return permanent;
    }
    
}
