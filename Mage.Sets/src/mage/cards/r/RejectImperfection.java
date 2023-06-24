package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetSpell;

import java.util.UUID;

public class RejectImperfection extends CardImpl {
    public RejectImperfection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        //Counter target spell. If that spell’s mana value was 3 or less, proliferate.
        this.getSpellAbility().addEffect(new RejectImperfectionEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private RejectImperfection(final RejectImperfection card) {
        super(card);
    }

    @Override
    public RejectImperfection copy() {
        return new RejectImperfection(this);
    }
}

class RejectImperfectionEffect extends OneShotEffect {

    public RejectImperfectionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If that spell's mana value was 3 or less, proliferate. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
    }

    private RejectImperfectionEffect(final RejectImperfectionEffect effect) {
        super(effect);
    }

    @Override
    public RejectImperfectionEffect copy() {
        return new RejectImperfectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getFirstTarget());
        if (object == null) {
            return false;
        }
        new CounterTargetEffect().apply(game, source);
        int manaValue = object.getManaValue();
        if (manaValue <= 3) {
            new ProliferateEffect().apply(game, source);
        }
        return true;
    }
}
