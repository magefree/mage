
package mage.cards.l;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class LilianaOfTheVeil extends CardImpl {

    public LilianaOfTheVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(3);

        // +1: Each player discards a card.
        this.addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(), 1));

        // -2: Target player sacrifices a creature.
        LoyaltyAbility ability = new LoyaltyAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -6: Separate all permanents target player controls into two piles. That player sacrifices all permanents in the pile of their choice.
        ability = new LoyaltyAbility(new LilianaOfTheVeilEffect(), -6);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LilianaOfTheVeil(final LilianaOfTheVeil card) {
        super(card);
    }

    @Override
    public LilianaOfTheVeil copy() {
        return new LilianaOfTheVeil(this);
    }
}

class LilianaOfTheVeilEffect extends OneShotEffect {

    public LilianaOfTheVeilEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Separate all permanents target player controls into two piles. That player sacrifices all permanents in the pile of their choice";
    }

    public LilianaOfTheVeilEffect(final LilianaOfTheVeilEffect effect) {
        super(effect);
    }

    @Override
    public LilianaOfTheVeilEffect copy() {
        return new LilianaOfTheVeilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            int count = game.getBattlefield().countAll(new FilterPermanent(), targetPlayer.getId(), game);
            TargetPermanent target = new TargetPermanent(0, count, new FilterPermanent("permanents to put in the first pile"), true);
            List<Permanent> pile1 = new ArrayList<>();
            target.setRequired(false);
            if (player.choose(Outcome.Neutral, target, source.getSourceId(), game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent p = game.getPermanent(targetId);
                    if (p != null) {
                        pile1.add(p);
                    }
                }
            }
            List<Permanent> pile2 = new ArrayList<>();
            for (Permanent p : game.getBattlefield().getAllActivePermanents(targetPlayer.getId())) {
                if (!pile1.contains(p)) {
                    pile2.add(p);
                }
            }

            boolean choice = targetPlayer.choosePile(Outcome.DestroyPermanent, "Choose a pile to sacrifice.", pile1, pile2, game);

            if (choice) {
                sacrificePermanents(pile1, game, source);
            } else {
                sacrificePermanents(pile2, game, source);
            }

            return true;
        }
        return false;
    }

    private void sacrificePermanents(List<Permanent> pile, Game game, Ability source) {
        for (Permanent permanent : pile) {
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
    }
}
