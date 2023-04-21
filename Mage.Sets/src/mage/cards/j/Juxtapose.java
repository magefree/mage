package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.*;

/**
 * @author Quercitron
 */
public final class Juxtapose extends CardImpl {

    public Juxtapose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // You and target player exchange control of the creature you each control with the highest converted mana cost. Then exchange control of artifacts the same way. If two or more permanents a player controls are tied for highest cost, their controller chooses one of them.
        this.getSpellAbility().addEffect(new JuxtaposeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "You and target player exchange control of the creature you each control with the highest mana value"));
        this.getSpellAbility().addEffect(new JuxtaposeEffect(new FilterArtifactPermanent(), "Then exchange control of artifacts the same way. If two or more permanents a player controls are tied for highest, their controller chooses one of them"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Juxtapose(final Juxtapose card) {
        super(card);
    }

    @Override
    public Juxtapose copy() {
        return new Juxtapose(this);
    }
}

// effect is based on ExchangeControlTargetEffect
class JuxtaposeEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    private final Map<UUID, Integer> zoneChangeCounter;
    private final Map<UUID, UUID> lockedControllers;

    public JuxtaposeEffect(FilterPermanent filter, String text) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = text;
        this.filter = filter;

        this.zoneChangeCounter = new HashMap<>();
        this.lockedControllers = new HashMap<>();
    }

    public JuxtaposeEffect(final JuxtaposeEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.zoneChangeCounter = new HashMap<>(effect.zoneChangeCounter);
        this.lockedControllers = new HashMap<>(effect.lockedControllers);
    }

    @Override
    public JuxtaposeEffect copy() {
        return new JuxtaposeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));

        if (you != null && targetPlayer != null) {
            Permanent permanent1 = chooseOnePermanentsWithTheHighestCMC(you, filter, source, game);
            Permanent permanent2 = chooseOnePermanentsWithTheHighestCMC(targetPlayer, filter, source, game);

            if (permanent1 != null && permanent2 != null) {
                // exchange works only for two different controllers
                if (permanent1.isControlledBy(permanent2.getControllerId())) {
                    // discard effect if controller of both permanents is the same
                    discard();
                    return;
                }
                this.lockedControllers.put(permanent1.getId(), permanent2.getControllerId());
                this.zoneChangeCounter.put(permanent1.getId(), permanent1.getZoneChangeCounter(game));
                this.lockedControllers.put(permanent2.getId(), permanent1.getControllerId());
                this.zoneChangeCounter.put(permanent2.getId(), permanent2.getZoneChangeCounter(game));

                permanent1.changeControllerId(targetPlayer.getId(), game, source);
                permanent2.changeControllerId(you.getId(), game, source);
                MageObject sourceObject = game.getCard(source.getSourceId());
                game.informPlayers((sourceObject != null ? sourceObject.getLogName() : "") + ": " + you.getLogName() +
                        " and " + targetPlayer.getLogName() + " exchange control of " + permanent1.getLogName() +
                        " and " + permanent2.getName());
            } else {
                // discard if there are less than 2 permanents
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toDelete = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry : zoneChangeCounter.entrySet()) {
            Permanent permanent = game.getPermanent(entry.getKey());
            if (permanent == null || permanent.getZoneChangeCounter(game) != entry.getValue()) {
                // controll effect cease if the same permanent is no longer on the battlefield
                toDelete.add(entry.getKey());
                continue;
            }
            permanent.changeControllerId(lockedControllers.get(permanent.getId()), game, source);
        }
        if (!toDelete.isEmpty()) {
            for (UUID uuid : toDelete) {
                zoneChangeCounter.remove(uuid);
            }
            if (zoneChangeCounter.isEmpty()) {
                discard();
                return false;
            }
        }
        return true;
    }

    private Permanent chooseOnePermanentsWithTheHighestCMC(Player player, FilterPermanent filter, Ability source, Game game) {
        List<Permanent> permanents = getPermanentsWithTheHighestCMC(player.getId(), filter, source, game);
        return chooseOnePermanent(player, permanents, source, game);
    }

    private List<Permanent> getPermanentsWithTheHighestCMC(UUID playerId, FilterPermanent filter, Ability source, Game game) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, playerId, game);
        int highestCMC = -1;
        for (Permanent permanent : permanents) {
            if (highestCMC < permanent.getManaValue()) {
                highestCMC = permanent.getManaValue();
            }
        }
        List<Permanent> result = new ArrayList<>();
        for (Permanent permanent : permanents) {
            if (permanent.getManaValue() == highestCMC) {
                result.add(permanent);
            }
        }
        return result;
    }

    private Permanent chooseOnePermanent(Player player, List<Permanent> permanents, Ability source, Game game) {
        Permanent permanent = null;
        if (permanents.size() == 1) {
            permanent = permanents.iterator().next();
        } else if (permanents.size() > 1) {
            Cards cards = new CardsImpl();
            for (Permanent card : permanents) {
                cards.add(card);
            }

            TargetCard targetCard = new TargetCard(Zone.BATTLEFIELD, new FilterCard());
            if (player.choose(Outcome.Benefit, cards, targetCard, source, game)) {
                permanent = game.getPermanent(targetCard.getFirstTarget());
            }
        }
        return permanent;
    }

}
