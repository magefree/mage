package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObNixilisTheHateTwisted extends CardImpl {

    public ObNixilisTheHateTwisted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIXILIS);
        this.setStartingLoyalty(5);

        // Whenever an opponent draws a card, Ob Nixilis, the Hate-Twisted deals 1 damage to that player.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DamageTargetEffect(
                1, true, "that player"
        ), false, true));

        // -2: Destroy target creature. Its controller draws two cards.
        Ability ability = new LoyaltyAbility(new ObNixilisTheHateTwistedEffect(), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ObNixilisTheHateTwisted(final ObNixilisTheHateTwisted card) {
        super(card);
    }

    @Override
    public ObNixilisTheHateTwisted copy() {
        return new ObNixilisTheHateTwisted(this);
    }
}

class ObNixilisTheHateTwistedEffect extends OneShotEffect {

    ObNixilisTheHateTwistedEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target creature. Its controller draws two cards";
    }

    private ObNixilisTheHateTwistedEffect(final ObNixilisTheHateTwistedEffect effect) {
        super(effect);
    }

    @Override
    public ObNixilisTheHateTwistedEffect copy() {
        return new ObNixilisTheHateTwistedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(2, source, game);
        return true;
    }
}