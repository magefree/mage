package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpineHoundmaster extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public AlpineHoundmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Alpine Houndmaster enters the battlefield, you may search your library for a card named Alpine Watchdog and/or a card named Igneous Cur, reveal them, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AlpineHoundmasterEffect(), true));

        // Whenever Alpine Houndmaster attacks, it gets +X/+0 until end of turn, where X is the number of other attacking creatures.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"), false));
    }

    private AlpineHoundmaster(final AlpineHoundmaster card) {
        super(card);
    }

    @Override
    public AlpineHoundmaster copy() {
        return new AlpineHoundmaster(this);
    }
}

class AlpineHoundmasterEffect extends OneShotEffect {

    AlpineHoundmasterEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a card named Alpine Watchdog and/or a card named Igneous Cur, " +
                "reveal them, put them into your hand, then shuffle";
    }

    private AlpineHoundmasterEffect(final AlpineHoundmasterEffect effect) {
        super(effect);
    }

    @Override
    public AlpineHoundmasterEffect copy() {
        return new AlpineHoundmasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new AlpineHoundmasterTarget();
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        player.revealCards(source, cards, game);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class AlpineHoundmasterTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("card named Alpine Watchdog and/or a card named Igneous Cur");

    static {
        filter.add(Predicates.or(
                new NamePredicate("Alpine Watchdog"),
                new NamePredicate("Igneous Cur")
        ));
    }

    AlpineHoundmasterTarget() {
        super(0, 2, filter);
    }

    private AlpineHoundmasterTarget(final AlpineHoundmasterTarget target) {
        super(target);
    }

    @Override
    public AlpineHoundmasterTarget copy() {
        return new AlpineHoundmasterTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .noneMatch(card.getName()::equals);
    }
}
