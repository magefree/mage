package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import mage.target.common.TargetCardInGraveyard;

/**
 * @author TheElk801
 */
public final class AncientBrassDragon extends CardImpl {

    public AncientBrassDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");

        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Ancient Brass Dragon deals combat damage to a player, roll a d20. When you do, put any number of target creature cards with total mana value X or less from graveyards onto the battlefield under your control, where X is the result.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AncientBrassDragonEffect(), false));
    }

    private AncientBrassDragon(final AncientBrassDragon card) {
        super(card);
    }

    @Override
    public AncientBrassDragon copy() {
        return new AncientBrassDragon(this);
    }
}

class AncientBrassDragonEffect extends OneShotEffect {

    AncientBrassDragonEffect() {
        super(Outcome.Benefit);
        staticText = "roll a d20. When you do, put any number of target creature cards with total mana value X "
                + "or less from graveyards onto the battlefield under your control, where X is the result";
    }

    private AncientBrassDragonEffect(final AncientBrassDragonEffect effect) {
        super(effect);
    }

    @Override
    public AncientBrassDragonEffect copy() {
        return new AncientBrassDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 20);
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new AncientBrassDragonTarget(result));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class AncientBrassDragonTarget extends TargetCardInGraveyard {

    private final int xValue;

    AncientBrassDragonTarget(int xValue) {
        super(0, Integer.MAX_VALUE, makeFilter(xValue), false);
        this.xValue = xValue;
    }

    private AncientBrassDragonTarget(final AncientBrassDragonTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public AncientBrassDragonTarget copy() {
        return new AncientBrassDragonTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .mapToInt(Card::getManaValue)
                        .sum() + card.getManaValue() <= xValue;
    }

    private static final FilterCard makeFilter(int xValue) {
        FilterCard filter = new FilterCreatureCard(
                "creature cards with total mana value "
                + xValue + " or less from graveyards"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue+1));
        return filter;
    }
}
