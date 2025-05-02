package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomDeadlyDevourer extends CardImpl {

    public VenomDeadlyDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // {3}: Exile target creature card from a graveyard. When you do, put X +1/+1 counters on target Symbiote, where X is the exiled card's toughness.
        Ability ability = new SimpleActivatedAbility(new VenomDeadlyDevourerEffect(), new GenericManaCost(3));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);
    }

    private VenomDeadlyDevourer(final VenomDeadlyDevourer card) {
        super(card);
    }

    @Override
    public VenomDeadlyDevourer copy() {
        return new VenomDeadlyDevourer(this);
    }
}

class VenomDeadlyDevourerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SYMBIOTE, "Symbiote");

    VenomDeadlyDevourerEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from a graveyard. When you do, " +
                "put X +1/+1 counters on target Symbiote, where X is the exiled card's toughness";
    }

    private VenomDeadlyDevourerEffect(final VenomDeadlyDevourerEffect effect) {
        super(effect);
    }

    @Override
    public VenomDeadlyDevourerEffect copy() {
        return new VenomDeadlyDevourerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        int toughness = card.getToughness().getValue();
        player.moveCards(card, Zone.EXILED, source, game);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(toughness)), false
        );
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
