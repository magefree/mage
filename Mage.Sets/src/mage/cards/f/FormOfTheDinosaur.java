
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FormOfTheDinosaur extends CardImpl {

    public FormOfTheDinosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");

        // When Form of the Dinosaur enters the battlefield, your life total becomes 15.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SetPlayerLifeSourceEffect(15), false));

        // At the beginning of your upkeep, Form of the Dinosaur deals 15 damage to target creature an opponent controls and that creature deals damage equal to its power to you.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new FormOfTheDinosaurEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public FormOfTheDinosaur(final FormOfTheDinosaur card) {
        super(card);
    }

    @Override
    public FormOfTheDinosaur copy() {
        return new FormOfTheDinosaur(this);
    }
}

class FormOfTheDinosaurEffect extends OneShotEffect {

    public FormOfTheDinosaurEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 15 damage to target creature an opponent controls and that creature deals damage equal to its power to you";
    }

    public FormOfTheDinosaurEffect(final FormOfTheDinosaurEffect effect) {
        super(effect);
    }

    @Override
    public FormOfTheDinosaurEffect copy() {
        return new FormOfTheDinosaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                targetCreature.damage(15, source.getSourceId(), game, false, true);
                controller.damage(targetCreature.getPower().getValue(), targetCreature.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
