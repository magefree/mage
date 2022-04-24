package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author noahg
 */
public final class Aetherplasm extends CardImpl {

    public Aetherplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Aetherplasm blocks a creature, you may return Aetherplasm to its owner's hand.
        // If you do, you may put a creature card from your hand onto the battlefield blocking that creature.
        this.addAbility(new BlocksCreatureTriggeredAbility(new DoIfCostPaid(
                new AetherplasmEffect(), new ReturnToHandFromBattlefieldSourceCost()
        )));
    }

    private Aetherplasm(final Aetherplasm card) {
        super(card);
    }

    @Override
    public Aetherplasm copy() {
        return new Aetherplasm(this);
    }
}

class AetherplasmEffect extends OneShotEffect {

    public AetherplasmEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card from your hand onto the battlefield blocking that creature";
    }

    public AetherplasmEffect(AetherplasmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.PutCardInPlay, "Put a creature card from your hand onto the battlefield?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A);
            if (player.choose(Outcome.PutCardInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Permanent blockedCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                    if (player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null)
                            && game.getCombat() != null && blockedCreature != null) {
                        CombatGroup attacker = game.getCombat().findGroup(blockedCreature.getId());
                        Permanent putIntoPlay = game.getPermanent(target.getFirstTarget());
                        if (putIntoPlay != null && putIntoPlay.isCreature(game) && attacker != null) {
                            game.getCombat().findGroup(blockedCreature.getId()).addBlocker(putIntoPlay.getId(), source.getControllerId(), game);

                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public AetherplasmEffect copy() {
        return new AetherplasmEffect(this);
    }
}
