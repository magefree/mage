package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.command.emblems.TezzeretCruelCaptainEmblem;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TezzeretCruelCaptain extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact card with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public TezzeretCruelCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);
        this.setStartingLoyalty(4);

        // Whenever an artifact you control enters, put a loyalty counter on Tezzeret.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ));

        // 0: Untap target artifact or creature. If it's an artifact creature, put a +1/+1 counter on it.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 0);
        ability.addEffect(new TezzeretCruelCaptainEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // -3: Search your library for an artifact card with mana value 1 or less, reveal it, put it in your hand, then shuffle.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), -3));

        // -7: You get an emblem with "At the beginning of combat on your turn, put three +1/+1 counters on target artifact you control. If it's not a creature, it becomes a 0/0 Robot artifact creature."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TezzeretCruelCaptainEmblem()), -7));
    }

    private TezzeretCruelCaptain(final TezzeretCruelCaptain card) {
        super(card);
    }

    @Override
    public TezzeretCruelCaptain copy() {
        return new TezzeretCruelCaptain(this);
    }
}

class TezzeretCruelCaptainEffect extends OneShotEffect {

    TezzeretCruelCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "If it's an artifact creature, put a +1/+1 counter on it";
    }

    private TezzeretCruelCaptainEffect(final TezzeretCruelCaptainEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretCruelCaptainEffect copy() {
        return new TezzeretCruelCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(this.getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.isArtifact(game) && permanent.isCreature(game))
                .filter(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(), source, game))
                .isPresent();
    }
}
