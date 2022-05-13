package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SizzlingSoloist extends CardImpl {

    public SizzlingSoloist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Alliance — Whenever another creature enters the battlefield under your control, target creature an opponent controls can't block this turn. If this is the second time this ability has resolved this turn, that creature attacks during its controller's next combat phase if able.
        Ability ability = new AllianceAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.Benefit, 2, new SizzlingSoloistEffect()
        ));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private SizzlingSoloist(final SizzlingSoloist card) {
        super(card);
    }

    @Override
    public SizzlingSoloist copy() {
        return new SizzlingSoloist(this);
    }
}

class SizzlingSoloistEffect extends RequirementEffect {

    public SizzlingSoloistEffect() {
        super(Duration.Custom);
        staticText = "that creature attacks during its controller's next combat phase if able";
    }

    public SizzlingSoloistEffect(final SizzlingSoloistEffect effect) {
        super(effect);
    }

    @Override
    public SizzlingSoloistEffect copy() {
        return new SizzlingSoloistEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPermanent(getTargetPointer().getFirst(game, source)) == null) {
            return true;
        }
        return game.isActivePlayer(game.getControllerId(getTargetPointer().getFirst(game, source)))
                && game.getPhase().getType() == TurnPhase.COMBAT
                && game.getStep().getType() == PhaseStep.END_COMBAT;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
