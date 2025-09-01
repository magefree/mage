package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMasterFormedAnew extends CardImpl {

    public TheMasterFormedAnew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Body Thief -- When you cast this spell, you may exile a creature you control and put a takeover counter on it.
        this.addAbility(new CastSourceTriggeredAbility(new TheMasterFormedAnewExileEffect()).withFlavorWord("Body Thief"));

        // You may have The Master, Formed Anew enter the battlefield as a copy of a creature card in exile with a takeover counter on it.
        this.addAbility(new EntersBattlefieldAbility(new TheMasterFormedAnewCopyEffect(), true));
    }

    private TheMasterFormedAnew(final TheMasterFormedAnew card) {
        super(card);
    }

    @Override
    public TheMasterFormedAnew copy() {
        return new TheMasterFormedAnew(this);
    }
}

class TheMasterFormedAnewExileEffect extends OneShotEffect {

    TheMasterFormedAnewExileEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile a creature you control and put a takeover counter on it";
    }

    private TheMasterFormedAnewExileEffect(final TheMasterFormedAnewExileEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterFormedAnewExileEffect copy() {
        return new TheMasterFormedAnewExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(0, 1);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card != null && Zone.EXILED.match(game.getState().getZone(card.getId()))) {
            card.addCounters(CounterType.TAKEOVER.createInstance(), source, game);
        }
        return true;
    }
}

class TheMasterFormedAnewCopyEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("a creature card in exile with a takeover counter on it");

    static {
        filter.add(CounterType.TAKEOVER.getPredicate());
    }

    TheMasterFormedAnewCopyEffect() {
        super(Outcome.Benefit);
        staticText = "as a copy of a creature card in exile with a takeover counter on it";
    }

    private TheMasterFormedAnewCopyEffect(final TheMasterFormedAnewCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterFormedAnewCopyEffect copy() {
        return new TheMasterFormedAnewCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(0, 1, filter);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.addEffect(new CopyEffect(Duration.Custom, card, source.getSourceId()), source);
        return true;
    }
}
