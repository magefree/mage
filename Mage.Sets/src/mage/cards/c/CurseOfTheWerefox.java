package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.RoleType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CurseOfTheWerefox extends CardImpl {

    public CurseOfTheWerefox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a Monster Role token attached to target creature you control. When you do, that creature fights up to one target creature you don't control.
        this.getSpellAbility().addEffect(new CreateRoleAttachedTargetEffect(RoleType.MONSTER));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new CurseOfTheWerefoxEffect());
    }

    private CurseOfTheWerefox(final CurseOfTheWerefox card) {
        super(card);
    }

    @Override
    public CurseOfTheWerefox copy() {
        return new CurseOfTheWerefox(this);
    }
}

class CurseOfTheWerefoxEffect extends OneShotEffect {

    CurseOfTheWerefoxEffect() {
        super(Outcome.Benefit);
        staticText = "when you do, that creature fights up to one target creature you don't control";
    }

    private CurseOfTheWerefoxEffect(final CurseOfTheWerefoxEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheWerefoxEffect copy() {
        return new CurseOfTheWerefoxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new FightTargetsEffect(false), false,
                "that creature fights up to one target creature you don't control"
        );
        ability.addTarget(source.getTargets().get(0));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
