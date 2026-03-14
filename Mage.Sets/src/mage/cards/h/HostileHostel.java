package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class HostileHostel extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.SOUL, 3);

    public HostileHostel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.LAND}, new SubType[]{}, "",
                "Creeping Inn",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.HORROR, SubType.CONSTRUCT}, "B"
        );

        // Hostile Hostel
        // {T}: Add {C}.
        this.getLeftHalfCard().addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice a creature: Put a soul counter on Hostile Hostel. Then if there are three or more soul counters on it, remove those counters, transform it, then untap it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new AddCountersSourceEffect(CounterType.SOUL.createInstance()), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.SOUL), condition, "Then if there are three " +
                "or more soul counters on it, remove those counters, transform it, then untap it"
        ).addEffect(new TransformSourceEffect()).addEffect(new UntapSourceEffect()));
        this.getLeftHalfCard().addAbility(ability);

        // Creeping Inn
        this.getRightHalfCard().setPT(3, 7);

        // Whenever Creeping Inn attacks, you may exile a creature card from your graveyard.
        // If you do, each opponent loses X life and you gain X life, where X is the number of creature cards exiled with Creeping Inn.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new CreepingInnEffect()));

        // {4}: Creeping Inn phases out.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new PhaseOutSourceEffect(), new ManaCostsImpl<>("{4}")));
    }

    private HostileHostel(final HostileHostel card) {
        super(card);
    }

    @Override
    public HostileHostel copy() {
        return new HostileHostel(this);
    }
}

class CreepingInnEffect extends OneShotEffect {

    CreepingInnEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile a creature card from your graveyard. " +
                "If you do, each opponent loses X life and you gain X life, " +
                "where X is the number of creature cards exiled with {this}.";
    }

    private CreepingInnEffect(final CreepingInnEffect effect) {
        super(effect);
    }

    @Override
    public CreepingInnEffect copy() {
        return new CreepingInnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player != null && permanent != null) {
            UUID exileId = CardUtil.getExileZoneId(game, source);
            TargetCardInGraveyard target = new TargetCardInGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.withNotTarget(true);
            if (target.canChoose(player.getId(), source, game)) {
                if (player.choose(Outcome.Exile, target, source, game)) {
                    Card cardChosen = game.getCard(target.getFirstTarget());
                    if (cardChosen != null) {
                        int lifeAmount = 0;
                        player.moveCardsToExile(cardChosen, source, game, true, exileId, permanent.getName());
                        ExileZone exile = game.getExile().getExileZone(exileId);
                        if (exile != null) {
                            for (UUID cardId : exile) {
                                lifeAmount++;
                            }
                        }
                        for (UUID playerId : game.getOpponents(source.getControllerId())) {
                            game.getPlayer(playerId).loseLife(lifeAmount, game, source, false);
                        }
                        player.gainLife(lifeAmount, game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
