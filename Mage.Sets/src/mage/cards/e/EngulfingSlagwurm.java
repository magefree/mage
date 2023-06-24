
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class EngulfingSlagwurm extends CardImpl {

    public EngulfingSlagwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever Engulfing Slagwurm blocks or becomes blocked by a creature, destroy that creature. You gain life equal to that creature's toughness.
        Ability ability = new BlocksOrBlockedByCreatureSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addEffect(new EngulfingSlagwurmEffect());
        this.addAbility(ability);
    }

    private EngulfingSlagwurm(final EngulfingSlagwurm card) {
        super(card);
    }

    @Override
    public EngulfingSlagwurm copy() {
        return new EngulfingSlagwurm(this);
    }

}

class EngulfingSlagwurmEffect extends OneShotEffect {

    EngulfingSlagwurmEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to that creature's toughness";
    }

    EngulfingSlagwurmEffect(final EngulfingSlagwurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null && controller != null) {
            controller.gainLife(creature.getPower().getValue(), game, source);
        }
        return false;
    }

    @Override
    public EngulfingSlagwurmEffect copy() {
        return new EngulfingSlagwurmEffect(this);
    }

}
