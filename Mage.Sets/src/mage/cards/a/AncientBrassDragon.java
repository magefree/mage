package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

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

        // Whenever Ancient Brass Dragon deals combat damage to a player, roll a d20. When you do, put any number of target creature cards with mana value X or less from graveyards onto the battlefield under your control, where X is the result.
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
        staticText = "roll a d20. When you do, put any number of target creature cards with mana value X " +
                "or less from graveyards onto the battlefield under your control, where X is the result";
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
        FilterCard filter = new FilterCreatureCard("creature cards with mana value " + result + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, result));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInGraveyard(0, Integer.MAX_VALUE, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
