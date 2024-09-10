package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.emblems.NissaWhoShakesTheWorldEmblem;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NissaWhoShakesTheWorld extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledLandPermanent("noncreature land you control");
    private static final FilterCard filter2 = new FilterCard("Forest cards");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(SubType.FOREST.getPredicate());
    }

    public NissaWhoShakesTheWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.setStartingLoyalty(5);

        // Whenever you tap a Forest for mana, add an additional {G}.
        this.addAbility(new NissaWhoShakesTheWorldTriggeredAbility());

        // +1: Put three +1/+1 counters on up to one target noncreature land you control. Untap it. It becomes a 0/0 Elemental creature with vigilance and haste that's still a land.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(3)
        ), 1);
        ability.addEffect(new UntapTargetEffect().setText("Untap it."));
        ability.addEffect(new BecomesCreatureTargetEffect(
                new NissaWhoShakesTheWorldToken(), false, true, Duration.Custom
        ).setText("It becomes a 0/0 Elemental creature with vigilance and haste that's still a land."));
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -8: You get an emblem with "Lands you control have indestructible." Search your library for any number of Forest cards, put them onto the battlefield tapped, then shuffle your library.
        ability = new LoyaltyAbility(new GetEmblemEffect(new NissaWhoShakesTheWorldEmblem()), -8);
        ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, Integer.MAX_VALUE, filter2
        ), true));
        this.addAbility(ability);
    }

    private NissaWhoShakesTheWorld(final NissaWhoShakesTheWorld card) {
        super(card);
    }

    @Override
    public NissaWhoShakesTheWorld copy() {
        return new NissaWhoShakesTheWorld(this);
    }
}

class NissaWhoShakesTheWorldTriggeredAbility extends TriggeredManaAbility {

    NissaWhoShakesTheWorldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)), false);
    }

    private NissaWhoShakesTheWorldTriggeredAbility(final NissaWhoShakesTheWorldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.hasSubtype(SubType.FOREST, game);
    }

    @Override
    public NissaWhoShakesTheWorldTriggeredAbility copy() {
        return new NissaWhoShakesTheWorldTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Forest for mana, add an additional {G}.";
    }
}

class NissaWhoShakesTheWorldToken extends TokenImpl {

    NissaWhoShakesTheWorldToken() {
        super("", "0/0 Elemental creature with vigilance and haste that's still a land.");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    private NissaWhoShakesTheWorldToken(final NissaWhoShakesTheWorldToken token) {
        super(token);
    }

    public NissaWhoShakesTheWorldToken copy() {
        return new NissaWhoShakesTheWorldToken(this);
    }
}
