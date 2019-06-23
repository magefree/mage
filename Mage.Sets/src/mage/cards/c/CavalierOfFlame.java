package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfFlame extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND);
    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public CavalierOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // {1}{R}: Creatures you control get +1/+0 and gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn
        ).setText("Creatures you control get +1/+0"), new ManaCostsImpl("{1}{R}"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain haste until end of turn"));
        this.addAbility(ability);

        // When Cavalier of Flame enters the battlefield, discard any number of cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CavalierOfFlameEffect()));

        // When Cavalier of Flame dies, it deals X damage to each opponent and each planeswalker they control, where X is the number of land cards in your graveyard.
        ability = new DiesTriggeredAbility(new DamagePlayersEffect(
                Outcome.Damage, xValue, TargetController.OPPONENT
        ).setText("it deals X damage to each opponent"));
        ability.addEffect(new DamageAllEffect(
                xValue, filter
        ).setText("and each planeswalker they control, where X is the number of land cards in your graveyard"));
        this.addAbility(ability);
    }

    private CavalierOfFlame(final CavalierOfFlame card) {
        super(card);
    }

    @Override
    public CavalierOfFlame copy() {
        return new CavalierOfFlame(this);
    }
}

class CavalierOfFlameEffect extends OneShotEffect {

    CavalierOfFlameEffect() {
        super(Outcome.Benefit);
        staticText = "discard any number of cards, then draw that many cards.";
    }

    private CavalierOfFlameEffect(final CavalierOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public CavalierOfFlameEffect copy() {
        return new CavalierOfFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, player.getHand().size(), StaticFilters.FILTER_CARD);
        if (player.choose(outcome, player.getHand(), target, game)) {
            int counter = target
                    .getTargets()
                    .stream()
                    .map(uuid -> game.getCard(uuid))
                    .mapToInt(card -> card != null && player.discard(card, source, game) ? 1 : 0)
                    .sum();
            player.drawCards(counter, game);
        }
        return true;
    }
}