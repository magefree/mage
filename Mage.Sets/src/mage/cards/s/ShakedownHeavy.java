package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShakedownHeavy extends CardImpl {

    public ShakedownHeavy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Shakedown Heavy attacks, defending player may have you draw a card. If they do, untap Shakedown Heavy and remove it from combat.
        this.addAbility(new AttacksTriggeredAbility(
                new ShakedownHeavyEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private ShakedownHeavy(final ShakedownHeavy card) {
        super(card);
    }

    @Override
    public ShakedownHeavy copy() {
        return new ShakedownHeavy(this);
    }
}

class ShakedownHeavyEffect extends OneShotEffect {

    ShakedownHeavyEffect() {
        super(Outcome.Benefit);
        staticText = "defending player may have you draw a card. If they do, untap {this} and remove it from combat";
    }

    private ShakedownHeavyEffect(final ShakedownHeavyEffect effect) {
        super(effect);
    }

    @Override
    public ShakedownHeavyEffect copy() {
        return new ShakedownHeavyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || defender == null || !controller.chooseUse(
                outcome, "Have " + controller.getName() + " draw a card?", source, game
        )) {
            return false;
        }
        controller.drawCards(1, source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.untap(game);
            permanent.removeFromCombat(game);
        }
        return true;
    }
}
