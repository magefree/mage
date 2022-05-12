
package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ExertInfluence extends CardImpl {

    public ExertInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // <i>Converge</i>-Gain control of target creature if its power is less than or equal to the number of colors spent to cast Exert Influence.
        getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
        getSpellAbility().addEffect(new ExertInfluenceEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private ExertInfluence(final ExertInfluence card) {
        super(card);
    }

    @Override
    public ExertInfluence copy() {
        return new ExertInfluence(this);
    }
}

class ExertInfluenceEffect extends OneShotEffect {

    public ExertInfluenceEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of target creature if its power is less than or equal to the number of colors of mana spent to cast this spell";
    }

    public ExertInfluenceEffect(final ExertInfluenceEffect effect) {
        super(effect);
    }

    @Override
    public ExertInfluenceEffect copy() {
        return new ExertInfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null) {
            int colors = new ColorsOfManaSpentToCastCount().calculate(game, source, this);
            if (targetCreature.getPower().getValue() <= colors) {
                game.addEffect(new GainControlTargetEffect(Duration.Custom, true), source);
            }
            return true;
        }
        return false;
    }
}
