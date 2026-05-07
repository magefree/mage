package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class ProbeDroid extends CardImpl {

    public ProbeDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Probe Droid enters the battlefield, target player reveals their hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ProbeDroidEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private ProbeDroid(final ProbeDroid card) {
        super(card);
    }

    @Override
    public ProbeDroid copy() {
        return new ProbeDroid(this);
    }
}

class ProbeDroidEffect extends OneShotEffect {

    ProbeDroidEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals their hand";
    }

    private ProbeDroidEffect(final ProbeDroidEffect effect) {
        super(effect);
    }

    @Override
    public ProbeDroidEffect copy() {
        return new ProbeDroidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        return true;
    }
}
