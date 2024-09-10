package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AloraMerryThief extends CardImpl {

    public AloraMerryThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you attack, up to one target attacking creature can't be blocked this turn. Return that creature to its owner's hand at the beginning of the next end step.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), 1
        );
        ability.addEffect(new AloraMerryThiefEffect());
        ability.addTarget(new TargetAttackingCreature(0, 1));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private AloraMerryThief(final AloraMerryThief card) {
        super(card);
    }

    @Override
    public AloraMerryThief copy() {
        return new AloraMerryThief(this);
    }
}

class AloraMerryThiefEffect extends OneShotEffect {

    AloraMerryThiefEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return that creature to its owner's hand at the beginning of the next end step";
    }

    private AloraMerryThiefEffect(final AloraMerryThiefEffect effect) {
        super(effect);
    }

    @Override
    public AloraMerryThiefEffect copy() {
        return new AloraMerryThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        DelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandTargetEffect()
                .setTargetPointer(new FixedTarget(creature, game))
                .setText("return that creature to its owner's hand"));
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }

}
