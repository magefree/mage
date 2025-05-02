package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.Targets;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class TheAnimus extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");
    private static final FilterCard exileFilter = new FilterCreatureCard("creature card in exile with a memory counter on it");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        exileFilter.add(CounterType.MEMORY.getPredicate());
    }

    public TheAnimus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // At the beginning of your end step, exile up to one target legendary creature card from a graveyard with a memory counter on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new TheAnimusEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        this.addAbility(ability);

        // {T}: Until your next turn, target legendary creature you control becomes a copy of target creature card in exile with a memory counter on it. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new TheAnimusCopyEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY));
        ability.addTarget(new TargetCardInExile(1, 1, exileFilter));
        this.addAbility(ability);
    }

    private TheAnimus(final TheAnimus card) {
        super(card);
    }

    @Override
    public TheAnimus copy() {
        return new TheAnimus(this);
    }
}

class TheAnimusEffect extends OneShotEffect {

    TheAnimusEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target legendary creature card from a graveyard with a memory counter on it";
    }

    private TheAnimusEffect(final TheAnimusEffect effect) {
        super(effect);
    }

    @Override
    public TheAnimusEffect copy() {
        return new TheAnimusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetId = getTargetPointer().getFirst(game, source);
        if (controller == null || targetId == null) {
            return false;
        }

        Card card = game.getCard(targetId);
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        if (game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        card.addCounters(CounterType.MEMORY.createInstance(), source.getControllerId(), source, game);
        return true;
    }
}

class TheAnimusCopyEffect extends OneShotEffect {

    TheAnimusCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Until your next turn, target legendary creature you control becomes a copy of target creature card in exile with a memory counter on it";
    }

    private TheAnimusCopyEffect(final TheAnimusCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheAnimusCopyEffect copy() {
        return new TheAnimusCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Targets targets = source.getTargets();

        if (targets.size() != 2) {
            return false;
        }
        UUID target1Id = source.getTargets().get(0).getFirstTarget();
        UUID target2Id = source.getTargets().get(1).getFirstTarget();


        if (target2Id == null || target1Id == null) {
            return false;
        }
        Card copyFromPermanent = game.getCard(target2Id);
        if (sourcePermanent == null || copyFromPermanent == null) {
            return false;
        }

        ContinuousEffect copyEffect = new CopyEffect(Duration.EndOfTurn, copyFromPermanent.getMainCard(), target1Id);
        game.addEffect(copyEffect, source);
        return true;


    }
}
