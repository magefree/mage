
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ResoluteArchangel extends CardImpl {

    public ResoluteArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Resolute Angel enters the battlefield, if your life total is lower than your starting life total, it becomes equal to your starting life total.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(new ResoluteArchangelEffect(), ControllerLifeLowerThanStrtingLife.instance,
                "if your life total is less than your starting life total, it becomes equal to your starting life total")));
    }

    private ResoluteArchangel(final ResoluteArchangel card) {
        super(card);
    }

    @Override
    public ResoluteArchangel copy() {
        return new ResoluteArchangel(this);
    }
}

class ResoluteArchangelEffect extends OneShotEffect {

    public ResoluteArchangelEffect() {
        super(Outcome.Benefit);
        this.staticText = "if your life total is lower than your starting life total, it becomes equal to your starting life total";
    }

    public ResoluteArchangelEffect(final ResoluteArchangelEffect effect) {
        super(effect);
    }

    @Override
    public ResoluteArchangelEffect copy() {
        return new ResoluteArchangelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setLife(game.getStartingLife(), game, source);
            return true;
        }
        return false;
    }
}

enum ControllerLifeLowerThanStrtingLife implements Condition {

 instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.getLife() < game.getStartingLife();
        }
        return false;
    }
}
