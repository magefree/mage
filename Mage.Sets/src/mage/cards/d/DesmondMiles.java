package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.*;

import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author grimreap124
 */
public final class DesmondMiles extends CardImpl {

    private static final FilterCard filter = new FilterCard("Assassin card in your graveyard");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.ASSASSIN);
    private static final AdditiveDynamicValue assassinsCount;
    static {
        filter2.add(AnotherPredicate.instance);
        filter.add(SubType.ASSASSIN.getPredicate());
        assassinsCount = new AdditiveDynamicValue(new CardsInControllerGraveyardCount(filter), new PermanentsOnBattlefieldCount(filter2));
    }

    public DesmondMiles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Desmond Miles gets +1/+0 for each other Assassin you control and each Assassin card in your graveyard.
        Ability ability = new SimpleStaticAbility(new BoostSourceEffect(assassinsCount, StaticValue.get(0), Duration.WhileOnBattlefield)
                .setText("{this} gets +1/+0 for each other Assassin you control and each Assassin card in your graveyard"));
        this.addAbility(ability);

        // Whenever Desmond Miles deals combat damage to a player, surveil X, where X is the amount of damage it dealt to that player.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DesmondMilesEffect(SavedDamageValue.MANY,
                "where X is the amount of damage it dealt to that player.")));
    }

    private DesmondMiles(final DesmondMiles card) {
        super(card);
    }

    @Override
    public DesmondMiles copy() {
        return new DesmondMiles(this);
    }
}

class DesmondMilesEffect extends OneShotEffect {

    protected final DynamicValue amount;

    public DesmondMilesEffect(DynamicValue amount, String message) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = "surveil X" + (message.isEmpty() ? "" : ", ") + message;
    }

    private DesmondMilesEffect(final DesmondMilesEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DesmondMilesEffect copy() {
        return new DesmondMilesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = amount.calculate(game, source, this);
        return player.surveil(xValue, source, game);
    }
}