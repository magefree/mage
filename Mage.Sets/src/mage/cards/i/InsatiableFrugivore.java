package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class InsatiableFrugivore extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Foods");

    public InsatiableFrugivore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Insatiable Frugivore enters, create a Food token, then you may exile three cards from your graveyard. If you do, repeat this process.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InsatiableFrugivoreEffect()));

        // {3}{B}, Sacrifice X Foods: Creatures you control get +X/+0 and gain menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(GetXValue.instance, StaticValue.get(0), Duration.EndOfTurn)
                        .setText("creatures you control get +X/+0"),
                new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain menace until end of turn"));
        this.addAbility(ability);

    }

    private InsatiableFrugivore(final InsatiableFrugivore card) {
        super(card);
    }

    @Override
    public InsatiableFrugivore copy() {
        return new InsatiableFrugivore(this);
    }
}

class InsatiableFrugivoreEffect extends OneShotEffect {

    InsatiableFrugivoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a Food token, then you may exile three cards from your graveyard. If you do, repeat this process";
    }

    private InsatiableFrugivoreEffect(final InsatiableFrugivoreEffect effect) {
        super(effect);
    }

    @Override
    public InsatiableFrugivoreEffect copy() {
        return new InsatiableFrugivoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean repeat;
        do {
            new FoodToken().putOntoBattlefield(1, game, source);
            Cost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard(3));
            repeat = cost.canPay(source, source, controller.getId(), game)
                    && controller.chooseUse(Outcome.Exile, "Exile three cards from your graveyard?", source, game)
                    && cost.pay(source, game, source, controller.getId(), false);
        } while (repeat && controller.canRespond());
        return true;
    }
}
