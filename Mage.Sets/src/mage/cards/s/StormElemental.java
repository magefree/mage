package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class StormElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public StormElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}, Exile the top card of your library: Tap target creature with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new ExileTopCardLibraryCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {U}, Exile the top card of your library: If the exiled card is a snow land, Storm Elemental gets +1/+1 until end of turn.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StormElementalEffect(), new ManaCostsImpl<>("{U}"));
        ability2.addCost(new ExileTopCardLibraryCost());
        this.addAbility(ability2);
    }

    private StormElemental(final StormElemental card) {
        super(card);
    }

    @Override
    public StormElemental copy() {
        return new StormElemental(this);
    }
}

class StormElementalEffect extends OneShotEffect {

    private static final FilterLandCard filter = new FilterLandCard("snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public StormElementalEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If the exiled card is a snow land, {this} gets +1/+1 until end of turn";
    }

    public StormElementalEffect(final StormElementalEffect effect) {
        super(effect);
    }

    @Override
    public StormElementalEffect copy() {
        return new StormElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = null;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileTopCardLibraryCost) {
                    card = ((ExileTopCardLibraryCost) cost).getCard();
                }
            }
            if (card != null) {
                if (filter.match(card, game)) {
                    game.addEffect(new BoostSourceEffect(1, 1, Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}

class ExileTopCardLibraryCost extends CostImpl {

    Card card;

    public ExileTopCardLibraryCost() {
        this.text = "Exile the top card of your library";
    }

    public ExileTopCardLibraryCost(final ExileTopCardLibraryCost cost) {
        super(cost);
        this.card = cost.getCard();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                paid = controller.moveCards(card, Zone.EXILED, ability, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getLibrary().hasCards();
        }
        return false;
    }

    @Override
    public ExileTopCardLibraryCost copy() {
        return new ExileTopCardLibraryCost(this);
    }

    public Card getCard() {
        return card;
    }
}
