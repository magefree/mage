package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class DeadlockTrap extends CardImpl {

    public DeadlockTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Deadlock Trap enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Deadlock Trap enters the battlefield, you get {E}{E}
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // {t}, Pay {E}: Tap target creature or planeswalker. Its activated abilities can't be activated this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(1));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addEffect(new DeadlockTrapCantActivateEffect());
        this.addAbility(ability);

    }

    private DeadlockTrap(final DeadlockTrap card) {
        super(card);
    }

    @Override
    public DeadlockTrap copy() {
        return new DeadlockTrap(this);
    }
}

class DeadlockTrapCantActivateEffect extends RestrictionEffect {

    public DeadlockTrapCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "Its activated abilities can't be activated this turn";
    }

    private DeadlockTrapCantActivateEffect(final DeadlockTrapCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public DeadlockTrapCantActivateEffect copy() {
        return new DeadlockTrapCantActivateEffect(this);
    }
}
