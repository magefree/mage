
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public final class JarJarBinks extends CardImpl {

    public JarJarBinks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GUNGAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Jar jar Binks can't block.
        this.addAbility(new CantBlockAbility());

        // When Jar Jar Binks enter the battlefield, target opponent gains control of it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JarJarBinksEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // At the beggining of combat on your turn, tap the creature you control with the highest power. If two or more creatures are tied for the greatest power, you choose one of them.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new JarJarBinksTapEffect(), TargetController.YOU, false));
    }

    private JarJarBinks(final JarJarBinks card) {
        super(card);
    }

    @Override
    public JarJarBinks copy() {
        return new JarJarBinks(this);
    }
}

class JarJarBinksEffect extends OneShotEffect {

    public JarJarBinksEffect() {
        super(Outcome.GainControl);
        this.staticText = "target opponent gains control of it";
    }

    public JarJarBinksEffect(final JarJarBinksEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksEffect copy() {
        return new JarJarBinksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent jarJar = source.getSourcePermanentIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null && jarJar != null && opponent != null) {
            ContinuousEffect effect = new JarJarBinksGainControlSourceEffect();
            effect.setTargetPointer(getTargetPointer());
            game.addEffect(effect, source);
            game.informPlayers(jarJar.getName() + " is now controlled by " + opponent.getLogName());
            return true;
        }
        return false;
    }
}

class JarJarBinksGainControlSourceEffect extends ContinuousEffectImpl {

    public JarJarBinksGainControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public JarJarBinksGainControlSourceEffect(final JarJarBinksGainControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksGainControlSourceEffect copy() {
        return new JarJarBinksGainControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetOpponent = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && targetOpponent != null) {
            permanent.changeControllerId(targetOpponent, game, source);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}

class JarJarBinksTapEffect extends OneShotEffect {

    public JarJarBinksTapEffect() {
        super(Outcome.Tap);
        this.staticText = "tap the creature you control with the highest power. If two or more creatures are tied for the greatest power, you choose one of them";
    }

    public JarJarBinksTapEffect(final JarJarBinksTapEffect effect) {
        super(effect);
    }

    @Override
    public JarJarBinksTapEffect copy() {
        return new JarJarBinksTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            int highestPower = Integer.MIN_VALUE;
            boolean multipleExist = false;
            Permanent permanentToTap = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
                if (permanent.getPower().getValue() > highestPower) {
                    permanentToTap = permanent;
                    highestPower = permanent.getPower().getValue();
                    multipleExist = false;
                } else if (permanent.getPower().getValue() == highestPower) {
                    multipleExist = true;
                }
            }
            if (multipleExist) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("one of the creatures with the highest power");
                filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, highestPower));
                Target target = new TargetPermanent(filter);
                target.setNotTarget(true);
                if (target.canChoose(source.getControllerId(), source, game)) {
                    if (controller.choose(outcome, target, source, game)) {
                        permanentToTap = game.getPermanent(target.getFirstTarget());
                    }
                }
            }
            if (permanentToTap != null) {
                game.informPlayers(sourcePermanent.getName() + " chosen creature: " + permanentToTap.getName());
                return permanentToTap.tap(source, game);
            }
            return true;
        }

        return false;
    }
}
