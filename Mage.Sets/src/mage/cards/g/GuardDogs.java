
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public final class GuardDogs extends CardImpl {

    public GuardDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{W}, {T}: Choose a permanent you control.
        //              Prevent all combat damage target creature would deal this turn if it shares a color with that permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GuardDogsEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GuardDogs(final GuardDogs card) {
        super(card);
    }

    @Override
    public GuardDogs copy() {
        return new GuardDogs(this);
    }
}

class GuardDogsEffect extends PreventionEffectImpl {
    
    private TargetControlledPermanent controlledTarget;

    public GuardDogsEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        this.staticText = "Choose a permanent you control. " +
                          "Prevent all combat damage target creature would deal this turn if it shares a color with that permanent";
    }

    private GuardDogsEffect(final GuardDogsEffect effect) {
        super(effect);
        this.controlledTarget = effect.controlledTarget.copy();
    }

    @Override
    public void init(Ability source, Game game) {
        this.controlledTarget = new TargetControlledPermanent();
        this.controlledTarget.setNotTarget(true);
        this.controlledTarget.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public GuardDogsEffect copy() {
        return new GuardDogsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (this.used || !super.applies(event, source, game)) {
            return false;
        }
        MageObject mageObject = game.getObject(event.getSourceId());
        if (mageObject == null || controlledTarget.getFirstTarget() == null) {
            return false;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(controlledTarget.getFirstTarget());
        Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null || targetPermanent == null) {
            return false;
        }

        return this.getTargetPointer().getTargets(game, source).contains(event.getSourceId())
                && permanent.getColor(game).shares(targetPermanent.getColor(game));
    }
}
