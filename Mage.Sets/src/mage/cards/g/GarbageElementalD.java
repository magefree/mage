package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author spjspj
 */
public final class GarbageElementalD extends CardImpl {

    public GarbageElementalD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Cascade
        this.addAbility(new CascadeAbility());

        // When Garbage Elemental enters the battlefield, roll a six-sided die. Garbage Elemental deals damage equal to the result to target opponent.
        Ability ability = new EntersBattlefieldAbility(new GarbageElementalDEffect(),
            null,
            "When {this} enters the battlefield, roll a six-sided die. {this} deals damage equal to the result to target opponent",
            null);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private GarbageElementalD(final GarbageElementalD card) {
        super(card);
    }

    @Override
    public GarbageElementalD copy() {
        return new GarbageElementalD(this);
    }
}

class GarbageElementalDEffect extends OneShotEffect {

    GarbageElementalDEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die. {this} deals damage equal to the result to target opponent";
    }

    GarbageElementalDEffect(final GarbageElementalDEffect effect) {
        super(effect);
    }

    @Override
    public GarbageElementalDEffect copy() {
        return new GarbageElementalDEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            int damage = controller.rollDice(outcome, source, game, 6);
            return game.damagePlayerOrPermanent(opponent.getId(), damage, source.getId(), source, game, false, true) > 0;
        }
        return false;
    }
}
