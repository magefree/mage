package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Malanthrope extends CardImpl {

    public Malanthrope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Scavenge the Dead -- When Malanthrope enters the battlefield, exile target player's graveyard. Put a +1/+1 counter on Malanthrope for each creature card exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MalanthropeEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.withFlavorWord("Scavenge the Dead"));
    }

    private Malanthrope(final Malanthrope card) {
        super(card);
    }

    @Override
    public Malanthrope copy() {
        return new Malanthrope(this);
    }
}

class MalanthropeEffect extends OneShotEffect {

    MalanthropeEffect() {
        super(Outcome.Benefit);
        staticText = "exile target player's graveyard. Put a +1/+1 counter " +
                "on {this} for each creature card exiled this way";
    }

    private MalanthropeEffect(final MalanthropeEffect effect) {
        super(effect);
    }

    @Override
    public MalanthropeEffect copy() {
        return new MalanthropeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || player.getGraveyard().isEmpty()) {
            return false;
        }
        int count = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        player.moveCards(player.getGraveyard(), Zone.EXILED, source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && count > 0) {
            game.getState().processAction(game);
            permanent.addCounters(CounterType.P1P1.createInstance(count), source, game);
        }
        return true;
    }
}
