package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PestilenceDemon extends CardImpl {

    public PestilenceDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PestilenceDemonEffect(), new ManaCostsImpl<>("{B}")));
    }

    private PestilenceDemon(final PestilenceDemon card) {
        super(card);
    }

    @Override
    public PestilenceDemon copy() {
        return new PestilenceDemon(this);
    }

}

class PestilenceDemonEffect extends OneShotEffect {

    PestilenceDemonEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to each creature and each player";
    }

    PestilenceDemonEffect(final PestilenceDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : game.getBattlefield().getAllPermanentIds()) {
            Permanent p = game.getPermanent(permanentId);
            if (p != null && p.isCreature(game)) {
                p.damage(1, source.getSourceId(), source, game, false, true);
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player p = game.getPlayer(playerId);
            if (p != null) {
                p.damage(1, source.getSourceId(), source, game);
            }
        }
        return true;
    }

    @Override
    public PestilenceDemonEffect copy() {
        return new PestilenceDemonEffect(this);
    }

}
