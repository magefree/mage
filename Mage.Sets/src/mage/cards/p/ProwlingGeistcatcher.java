package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProwlingGeistcatcher extends CardImpl {

    public ProwlingGeistcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you sacrifice another creature, exile it. If that creature was a token, put a +1/+1 counter on Prowling Geistcatcher.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new ProwlingGeistcatcherExileEffect(),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        ));

        // When Prowling Geistcatcher leaves the battlefield, return each card exiled with it to the battlefield under your control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ProwlingGeistcatcherReturnEffect(), false));
    }

    private ProwlingGeistcatcher(final ProwlingGeistcatcher card) {
        super(card);
    }

    @Override
    public ProwlingGeistcatcher copy() {
        return new ProwlingGeistcatcher(this);
    }
}

class ProwlingGeistcatcherExileEffect extends OneShotEffect {

    ProwlingGeistcatcherExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If that creature was a token, put a +1/+1 counter on {this}";
    }

    private ProwlingGeistcatcherExileEffect(final ProwlingGeistcatcherExileEffect effect) {
        super(effect);
    }

    @Override
    public ProwlingGeistcatcherExileEffect copy() {
        return new ProwlingGeistcatcherExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player != null && card != null) {
            player.moveCardsToExile(
                    card, source, game, true,
                    CardUtil.getExileZoneId(game, source),
                    CardUtil.getSourceName(game, source)
            );
        }
        Permanent exiled = (Permanent) getValue("sacrificedPermanent");
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (exiled instanceof PermanentToken && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}

class ProwlingGeistcatcherReturnEffect extends OneShotEffect {

    ProwlingGeistcatcherReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return each card exiled with it to the battlefield under your control";
    }

    private ProwlingGeistcatcherReturnEffect(final ProwlingGeistcatcherReturnEffect effect) {
        super(effect);
    }

    @Override
    public ProwlingGeistcatcherReturnEffect copy() {
        return new ProwlingGeistcatcherReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null && exileZone != null && !exileZone.isEmpty()
                && player.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
    }
}
