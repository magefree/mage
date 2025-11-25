package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandbenderScavengers extends CardImpl {

    public SandbenderScavengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice another permanent, put a +1/+1 counter on this creature.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_ANOTHER_PERMANENT
        ));

        // When this creature dies, you may exile it. When you do, return target creature card with mana value less than or equal to this creature's power from your graveyard to the battlefield.
        this.addAbility(new DiesSourceTriggeredAbility(new SandbenderScavengersEffect()));
    }

    private SandbenderScavengers(final SandbenderScavengers card) {
        super(card);
    }

    @Override
    public SandbenderScavengers copy() {
        return new SandbenderScavengers(this);
    }
}

class SandbenderScavengersEffect extends OneShotEffect {

    SandbenderScavengersEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile it. When you do, return target creature card with mana value " +
                "less than or equal to this creature's power from your graveyard to the battlefield.";
    }

    private SandbenderScavengersEffect(final SandbenderScavengersEffect effect) {
        super(effect);
    }

    @Override
    public SandbenderScavengersEffect copy() {
        return new SandbenderScavengersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = Optional
                .ofNullable(source.getSourceObject(game))
                .filter(Card.class::isInstance)
                .map(Card.class::cast)
                .filter(c -> c.getZoneChangeCounter(game) != source.getStackMomentSourceZCC() + 1)
                .orElse(null);
        if (player == null || card == null || !player.chooseUse(
                Outcome.Exile, "Exile " + card.getName() + '?', source, game
        )) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        int mv = Optional
                .ofNullable((Permanent) getValue("permanentLeftBattlefield"))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + mv + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, mv + 1));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
