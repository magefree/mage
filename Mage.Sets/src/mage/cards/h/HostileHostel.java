package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class HostileHostel extends TransformingDoubleFacedCard {

    public HostileHostel(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Creeping Inn",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.HORROR, SubType.CONSTRUCT}, "B"
        );
        this.getRightHalfCard().setPT(3, 7);

        // {T}: Add {C}.
        this.getLeftHalfCard().addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice a creature: Put a soul counter on Hostile Hostel. Then if there are three or more soul counters on it, remove those counters, transform it, then untap it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new HostileHostelEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.getLeftHalfCard().addAbility(ability);

        // Creeping Inn
        // Whenever Creeping Inn attacks, you may exile a creature card from your graveyard. If you do, each opponent loses X life and you gain X life, where X is the number of creature cards exiled with Creeping Inn.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new CreepingInnEffect()));

        // {4}: Creeping Inn phases out.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new PhaseOutSourceEffect(), new GenericManaCost(4)));
    }

    private HostileHostel(final HostileHostel card) {
        super(card);
    }

    @Override
    public HostileHostel copy() {
        return new HostileHostel(this);
    }
}

class HostileHostelEffect extends OneShotEffect {

    HostileHostelEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a soul counter on {this}. Then if there are three or more " +
                "soul counters on it, remove those counters, transform it, then untap it.";
    }

    HostileHostelEffect(final HostileHostelEffect effect) {
        super(effect);
    }

    @Override
    public HostileHostelEffect copy() {
        return new HostileHostelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.SOUL.createInstance(), source.getControllerId(), source, game);
        int counters = permanent.getCounters(game).getCount(CounterType.SOUL);
        if (counters < 3) {
            return true;
        }
        permanent.removeCounters(CounterType.SOUL.getName(), counters, source, game);
        permanent.transform(source, game);
        permanent.untap(game);
        return true;
    }
}

class CreepingInnEffect extends OneShotEffect {

    public CreepingInnEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile a creature card from your graveyard. " +
                "If you do, each opponent loses X life and you gain X life, " +
                "where X is the number of creature cards exiled with Creeping Inn.";
    }

    public CreepingInnEffect(final CreepingInnEffect effect) {
        super(effect);
    }

    @Override
    public CreepingInnEffect copy() {
        return new CreepingInnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source);
        TargetCard target = new TargetCardInGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        );
        target.setNotTarget(true);
        player.choose(Outcome.Exile, target, source, game);
        Card cardChosen = game.getCard(target.getFirstTarget());
        if (cardChosen == null) {
            return false;
        }
        player.moveCardsToExile(cardChosen, source, game, true, exileId, CardUtil.getSourceName(game, source));
        ExileZone exile = game.getExile().getExileZone(exileId);
        int lifeAmount = exile != null ? exile.size() : 0;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            game.getPlayer(playerId).loseLife(lifeAmount, game, source, false);
        }
        player.gainLife(lifeAmount, game, source);
        return true;
    }
}
