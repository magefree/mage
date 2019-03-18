package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RevealSecretOpponentCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseSecretOpponentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 *
 * @author credman0
 */
public class EmissaryOfGrudges extends CardImpl {

    public EmissaryOfGrudges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // flying
        this.addAbility(FlyingAbility.getInstance());
        // haste
        this.addAbility(HasteAbility.getInstance());

        // As Emissary of Grudges enters the battlefield, secretly choose an opponent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new ChooseSecretOpponentEffect(),"As {this} enters the battlefield, secretly choose an opponent.")));
        // Choose new targets for target spell or ability if it’s controlled by the chosen player and if it targets you
        // or a permanent you control. Activate this ability only once.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EmissaryOfGrudgesEffect(), new RevealSecretOpponentCost());
        ability.addTarget(new TargetStackObject());
        this.addAbility(ability);
    }

    public EmissaryOfGrudges(final EmissaryOfGrudges card) {
        super(card);
    }

    @Override
    public EmissaryOfGrudges copy() {
        return new EmissaryOfGrudges(this);
    }
}

class EmissaryOfGrudgesEffect extends OneShotEffect {

    public EmissaryOfGrudgesEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose new targets for target spell or ability if it’s controlled by the chosen player and" +
                " if it targets you or a permanent you control. Activate this ability only once.";
    }

    public EmissaryOfGrudgesEffect(final EmissaryOfGrudgesEffect effect) {
        super(effect);
    }

    @Override
    public EmissaryOfGrudgesEffect copy() {
        return new EmissaryOfGrudgesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
            if (stackObject != null) {
                UUID opponentId = (UUID) game.getState().getValue(source.getSourceId() + ChooseSecretOpponentEffect.SECRET_OPPONENT);
                if (opponentId != null && opponentId.equals(stackObject.getControllerId())) {
                    // find if it targets you or a permanent you control
                    boolean targetsYouOrAPermanentYouControl = false;
                    for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                        Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                        for (Target target : mode.getTargets()) {
                            for (UUID targetId : target.getTargets()) {
                                if (source.isControlledBy(targetId)) {
                                    targetsYouOrAPermanentYouControl = true;
                                }
                                Permanent permanent = game.getPermanent(targetId);
                                if (permanent != null && source.isControlledBy(permanent.getControllerId())) {
                                    targetsYouOrAPermanentYouControl = true;
                                }
                            }
                        }
                    }
                    if (targetsYouOrAPermanentYouControl){
                        return stackObject.chooseNewTargets(game, source.getControllerId(), false, false, null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}