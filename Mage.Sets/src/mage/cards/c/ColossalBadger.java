package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossalBadger extends AdventureCard {

    public ColossalBadger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{G}", "Dig Deep", "{1}{G}");

        this.subtype.add(SubType.BADGER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Colossal Badger enters the battlefield, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // Dig Deep
        // Choose target creature. Mill four cards, then put a +1/+1 counter on that creature for each creature card milled this way.
        this.getSpellCard().getSpellAbility().addEffect(new ColossalBadgerEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ColossalBadger(final ColossalBadger card) {
        super(card);
    }

    @Override
    public ColossalBadger copy() {
        return new ColossalBadger(this);
    }
}

class ColossalBadgerEffect extends OneShotEffect {

    ColossalBadgerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature. Mill four cards, then put a +1/+1 counter " +
                "on that creature for each creature card milled this way";
    }

    private ColossalBadgerEffect(final ColossalBadgerEffect effect) {
        super(effect);
    }

    @Override
    public ColossalBadgerEffect copy() {
        return new ColossalBadgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.millCards(4, source, game).count(StaticFilters.FILTER_CARD_CREATURE, game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (amount > 0 && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        }
        return true;
    }
}
