
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class EntomberExarch extends CardImpl {

    public EntomberExarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Entomber Exarch enters the battlefield, choose one - Return target creature card from your graveyard to your hand; or target opponent reveals their hand, you choose a noncreature card from it, then that player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        Mode mode = new Mode();
        mode.addEffect(new EntomberExarchEffect());
        mode.addTarget(new TargetOpponent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public EntomberExarch(final EntomberExarch card) {
        super(card);
    }

    @Override
    public EntomberExarch copy() {
        return new EntomberExarch(this);
    }
}

class EntomberExarchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("noncreature card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    EntomberExarchEffect() {
        super(Outcome.Discard);
        staticText = "target opponent reveals their hand, you choose a noncreature card from it, then that player discards that card";
    }

    EntomberExarchEffect(final EntomberExarchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.revealCards("Entomber Exarch", player.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (you.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    return player.discard(card, source, game);

                }
            }
        }
        return false;
    }

    @Override
    public EntomberExarchEffect copy() {
        return new EntomberExarchEffect(this);
    }
}
