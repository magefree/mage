
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ThrullSurgeon extends CardImpl {

    public ThrullSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}, Sacrifice Thrull Surgeon: Look at target player's hand and choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ThrullSurgeonEffect(), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public ThrullSurgeon(final ThrullSurgeon card) {
        super(card);
    }

    @Override
    public ThrullSurgeon copy() {
        return new ThrullSurgeon(this);
    }
}

class ThrullSurgeonEffect extends OneShotEffect {

    public ThrullSurgeonEffect() {
        super(Outcome.Discard);
        staticText = "Look at target player's hand and choose a card from it. That player discards that card.";
    }

    public ThrullSurgeonEffect(final ThrullSurgeonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            you.lookAtCards("Discard", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
            target.setNotTarget(true);
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                return targetPlayer.discard(card, source, game);

            }

        }
        return false;
    }

    @Override
    public ThrullSurgeonEffect copy() {
        return new ThrullSurgeonEffect(this);
    }
}
