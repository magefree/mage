
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealSecretOpponentCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseSecretOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class StalkingLeonin extends CardImpl {

    public StalkingLeonin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAT, SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stalking Leonin enters the battlefield, secretly choose an opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChooseSecretOpponentEffect(), false));
        // Reveal the player you chose: Exile target creature that's attacking you if it's controlled by the chosen player. Activate this ability only once.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StalkingLeoninEffect(), new RevealSecretOpponentCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterCreatureAttackingYou()));
        this.addAbility(ability);
    }

    public StalkingLeonin(final StalkingLeonin card) {
        super(card);
    }

    @Override
    public StalkingLeonin copy() {
        return new StalkingLeonin(this);
    }
}

class StalkingLeoninEffect extends OneShotEffect {

    public StalkingLeoninEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target creature that's attacking you if it's controlled by the chosen player. Activate this ability only once";
    }

    public StalkingLeoninEffect(final StalkingLeoninEffect effect) {
        super(effect);
    }

    @Override
    public StalkingLeoninEffect copy() {
        return new StalkingLeoninEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                UUID opponentId = (UUID) game.getState().getValue(source.getSourceId() + ChooseSecretOpponentEffect.SECRET_OPPONENT);
                if (opponentId != null && opponentId.equals(targetCreature.getControllerId())) {
                    controller.moveCards(targetCreature, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
