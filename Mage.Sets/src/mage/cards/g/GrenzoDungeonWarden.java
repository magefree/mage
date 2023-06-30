
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class GrenzoDungeonWarden extends CardImpl {

    public GrenzoDungeonWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Grenzo, Dungeon Warden enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // {2}: Put the bottom card of your library into your graveyard. If it's a creature card with power less than or equal to Grenzo's power, put it onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrenzoDungeonWardenEffect(), new GenericManaCost(2)));
    }

    private GrenzoDungeonWarden(final GrenzoDungeonWarden card) {
        super(card);
    }

    @Override
    public GrenzoDungeonWarden copy() {
        return new GrenzoDungeonWarden(this);
    }
}

class GrenzoDungeonWardenEffect extends OneShotEffect {

    GrenzoDungeonWardenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put the bottom card of your library into your graveyard. If it's a creature card with power less than or equal to {this}'s power, put it onto the battlefield";
    }

    GrenzoDungeonWardenEffect(final GrenzoDungeonWardenEffect effect) {
        super(effect);
    }

    @Override
    public GrenzoDungeonWardenEffect copy() {
        return new GrenzoDungeonWardenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromBottom(game);
                if (card != null) {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    if (card.isCreature(game)) {
                        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
                        if (sourcePermanent != null && card.getPower().getValue() <= sourcePermanent.getPower().getValue()) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
