
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.token.GoblinSoldierToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class RiseOfTheHobgoblins extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Red creatures and white creatures");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public RiseOfTheHobgoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R/W}{R/W}");

        // When Rise of the Hobgoblins enters the battlefield, you may pay {X}. If you do, create X 1/1 red and white Goblin Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RiseOfTheHobgoblinsEffect()));

        // {RW}: Red creatures and white creatures you control gain first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter), new ManaCostsImpl<>("{R/W}")));

    }

    private RiseOfTheHobgoblins(final RiseOfTheHobgoblins card) {
        super(card);
    }

    @Override
    public RiseOfTheHobgoblins copy() {
        return new RiseOfTheHobgoblins(this);
    }
}

class RiseOfTheHobgoblinsEffect extends OneShotEffect {

    public RiseOfTheHobgoblinsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may pay {X}. If you do, create X 1/1 red and white Goblin Soldier creature tokens";
    }

    public RiseOfTheHobgoblinsEffect(final RiseOfTheHobgoblinsEffect effect) {
        super(effect);
    }

    @Override
    public RiseOfTheHobgoblinsEffect copy() {
        return new RiseOfTheHobgoblinsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        ManaCosts<ManaCost> cost = new ManaCostsImpl<>("{X}");
        if (you != null && you.chooseUse(Outcome.Neutral, "Do you want to to pay {X}?", source, game)) {
            int costX = you.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            cost.add(new GenericManaCost(costX));
            if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                Token token = new GoblinSoldierToken();
                return token.putOntoBattlefield(costX, game, source, source.getControllerId());
            }
        }
        return false;
    }
}
