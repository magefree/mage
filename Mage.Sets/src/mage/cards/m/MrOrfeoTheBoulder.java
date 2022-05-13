package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MrOrfeoTheBoulder extends CardImpl {

    public MrOrfeoTheBoulder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, double target creature's power until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new MrOrfeoTheBoulderEffect(), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MrOrfeoTheBoulder(final MrOrfeoTheBoulder card) {
        super(card);
    }

    @Override
    public MrOrfeoTheBoulder copy() {
        return new MrOrfeoTheBoulder(this);
    }
}

class MrOrfeoTheBoulderEffect extends OneShotEffect {

    MrOrfeoTheBoulderEffect() {
        super(Outcome.Benefit);
        staticText = "double target creature's power until end of turn";
    }

    private MrOrfeoTheBoulderEffect(final MrOrfeoTheBoulderEffect effect) {
        super(effect);
    }

    @Override
    public MrOrfeoTheBoulderEffect copy() {
        return new MrOrfeoTheBoulderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                permanent.getPower().getValue(), 0
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
