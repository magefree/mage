package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class GoblinCharbelcher extends CardImpl {

    public GoblinCharbelcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Reveal cards from the top of your library until you reveal a land card. Goblin Charbelcher deals damage equal to the number of nonland cards revealed this way to any target. If the revealed land card was a Mountain, Goblin Charbelcher deals double that damage instead. Put the revealed cards on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinCharbelcherEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinCharbelcher(final GoblinCharbelcher card) {
        super(card);
    }

    @Override
    public GoblinCharbelcher copy() {
        return new GoblinCharbelcher(this);
    }
}

class GoblinCharbelcherEffect extends OneShotEffect {

    public GoblinCharbelcherEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal cards from the top of your library until you reveal a land card. {this} deals damage equal to the number of nonland cards revealed this way to any target. If the revealed land card was a Mountain, {this} deals double that damage instead. Put the revealed cards on the bottom of your library in any order";
    }

    public GoblinCharbelcherEffect(final GoblinCharbelcherEffect effect) {
        super(effect);
    }

    @Override
    public GoblinCharbelcherEffect copy() {
        return new GoblinCharbelcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean isMountain = false;
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        boolean landFound = false;
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card != null) {
                cards.add(card);
                if (card.isLand(game)) {
                    landFound = true;
                    if (card.hasSubtype(SubType.MOUNTAIN, game)) {
                        isMountain = true;
                    }
                    break;
                }
            } else {
                break;
            }
        }

        controller.revealCards(sourceObject.getName(), cards, game);
        int damage = cards.size();
        if (landFound) {
            damage--;
        }
        if (isMountain) {
            damage *= 2;
        }

        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(damage, source.getSourceId(), source, game, false, true);
        } else {
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(damage, source.getSourceId(), source, game);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
