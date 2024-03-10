package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.counters.CounterType;
import mage.filter.common.FilterSuspendedCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.filter.FilterCard;
import mage.target.common.TargetSuspendedCard;

/**
 *
 * @author Skiwkr
 */
public final class AmyPond extends CardImpl {

    private static final FilterSuspendedCard filter = new FilterSuspendedCard("permanent you control or suspended card you own");
    static {
        filter.getCardFilter().add(TargetController.YOU.getOwnerPredicate());
    }

    public AmyPond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Rory Williams
        this.addAbility(new PartnerWithAbility("Rory Williams"));

        // Whenever Amy Pond deals combat damage to a player, choose a suspended card you own and remove that many time counters from it.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new AmyPondEffect(), false);
        ability.addTarget(new TargetSuspendedCard(filter, true));
        this.addAbility(ability);
        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private AmyPond(final AmyPond card) {
        super(card);
    }

    @Override
    public AmyPond copy() {
        return new AmyPond(this);
    }
}

class AmyPondEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("suspended card you own");

    static {
        filter.add(CounterType.TIME.getPredicate());
        filter.add(new AbilityPredicate(SuspendAbility.class));
    }
    AmyPondEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a suspended card you own and remove that many time counters from it";
    }

    private AmyPondEffect(final AmyPondEffect effect) {
        super(effect);
    }

    @Override
    public AmyPondEffect copy() {
        return new AmyPondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getExile().getCard(this.getTargetPointer().getFirst(game, source), game);
            if (card != null) {
                card.removeCounters(CounterType.TIME.toString(),card.getCounters(game).getCount(CounterType.TIME),source, game);
                return true;
                }
        }
        return false;
    }
}

