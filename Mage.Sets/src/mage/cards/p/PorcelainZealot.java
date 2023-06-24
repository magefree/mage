package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;


/**
 *
 * @author @stwalsh4118
 */
public final class PorcelainZealot extends CardImpl {

    public PorcelainZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target creature you control gets +1/+1 until end of turn. If that creature has toxic, instead it gets +2/+2 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            new PorcelainZealotEffect(),
            TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1));
        this.addAbility(ability);
    }

    private PorcelainZealot(final PorcelainZealot card) {
        super(card);
    }

    @Override
    public PorcelainZealot copy() {
        return new PorcelainZealot(this);
    }
}

class PorcelainZealotEffect extends OneShotEffect {

    public PorcelainZealotEffect() {
        super(Outcome.Benefit);
        this.staticText = "target creature you control gets +1/+1 until end of turn. If that creature has toxic, instead it gets +2/+2 until end of turn.";
    }

    public PorcelainZealotEffect(final PorcelainZealotEffect effect) {
        super(effect);
    }

    @Override
    public PorcelainZealotEffect copy() {
        return new PorcelainZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }

        Boolean targetHasToxic = creature.getAbilities().containsClass(ToxicAbility.class);

        if(targetHasToxic) {
            game.addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn), source);
        } else {
            game.addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn), source);
        }

        return true;
    }
}
