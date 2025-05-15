package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author balazskristof
 */
public final class YshtolaRhul extends CardImpl {

    public YshtolaRhul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, exile target creature you control, then return it to the battlefield under its owner's control. Then if it's the first end step of the turn, there is an additional end step after this step.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ExileThenReturnTargetEffect(true, false));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new YshtolaRhulEffect().concatBy("Then"));
        this.addAbility(ability);
    }

    private YshtolaRhul(final YshtolaRhul card) {
        super(card);
    }

    @Override
    public YshtolaRhul copy() {
        return new YshtolaRhul(this);
    }
}

class YshtolaRhulEffect extends OneShotEffect {
    public YshtolaRhulEffect() {
        super(Outcome.Benefit);
        staticText = "if it's the first end step of the turn, there is an additional end step after this step";
    }

    protected YshtolaRhulEffect(final YshtolaRhulEffect effect) {
        super(effect);
    }

    @Override
    public YshtolaRhulEffect copy() {
        return new YshtolaRhulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getTurn().getPhase(TurnPhase.END).getCount() == 0) {
            TurnMod end = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.END);
            game.getState().getTurnMods().add(end);
        }
        return true;
    }
}
