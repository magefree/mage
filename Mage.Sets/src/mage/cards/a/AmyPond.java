package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterSuspendedCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

import java.util.UUID;

/**
 *
 * @author Skiwkr
 */
public final class AmyPond extends CardImpl {

    private static final FilterSuspendedCard filter = new FilterSuspendedCard("suspended card you own");
    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
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
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new OneShotNonTargetEffect(
                new AmyPondEffect(SavedDamageValue.MANY), new TargetCardInExile(filter)),
                false, true));

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

    private final DynamicValue numberCounters;

    AmyPondEffect(DynamicValue numberCounters) {
        super(Outcome.Benefit);
        this.numberCounters = numberCounters;
        this.staticText = "choose a suspended card you own and remove that many time counters from it";
    }

    private AmyPondEffect(final AmyPondEffect effect) {
        super(effect);
        this.numberCounters = effect.numberCounters;
    }

    @Override
    public AmyPondEffect copy() {
        return new AmyPondEffect(this);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getExile().getCard(source.getFirstTarget(), game);
            if (card != null) {
                card.removeCounters(CounterType.TIME.toString(), (Integer) getValue("damage"), source, game);
                return true;
            }
        }
        return false;
    }
}
