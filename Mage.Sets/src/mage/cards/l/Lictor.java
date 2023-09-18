package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TyranidToken;
import mage.game.permanent.token.TyranidWarriorToken;
import mage.watchers.common.CreatureEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Lictor extends CardImpl {

    public Lictor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Pheromone Trail -- When Lictor enters the battlefield, if a creature entered the battlefield under an opponent's control this turn, create a 3/3 green Tyranid Warrior creature token with trample.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TyranidWarriorToken())),
                LictorCondition.instance, "When {this} enters the battlefield, " +
                "if a creature entered the battlefield under an opponent's control this turn, " +
                "create a 3/3 green Tyranid Warrior creature token with trample."
        ).withFlavorWord("Pheromone Trail"), new CreatureEnteredControllerWatcher());
    }

    private Lictor(final Lictor card) {
        super(card);
    }

    @Override
    public Lictor copy() {
        return new Lictor(this);
    }
}

enum LictorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getOpponents(source.getControllerId())
                .stream()
                .anyMatch(uuid -> CreatureEnteredControllerWatcher.enteredCreatureForPlayer(uuid, game));
    }
}
