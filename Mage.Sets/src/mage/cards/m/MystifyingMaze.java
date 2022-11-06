package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class MystifyingMaze extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MystifyingMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // {T}: Add Colorless.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Exile target attacking creature an opponent controls. At the beginning of the next end step, return it to the battlefield tapped under its owner's control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MystifyingMazeEffect(), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private MystifyingMaze(final MystifyingMaze card) {
        super(card);
    }

    @Override
    public MystifyingMaze copy() {
        return new MystifyingMaze(this);
    }
}

class MystifyingMazeEffect extends OneShotEffect {

    public MystifyingMazeEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target attacking creature an opponent controls. At the beginning of the next end step, return it to the battlefield tapped under its owner's control";
    }

    public MystifyingMazeEffect(final MystifyingMazeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source, game)) {
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false);
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public MystifyingMazeEffect copy() {
        return new MystifyingMazeEffect(this);
    }
}
