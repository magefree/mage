package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

public class SerumSnare extends CardImpl {
    public SerumSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        //Return target nonland permanent to its owner’s hand. If that permanent had mana value 3 or less, proliferate.
        this.getSpellAbility().addEffect(new SerumSnareEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SerumSnare(final SerumSnare card) {
        super(card);
    }

    @Override
    public SerumSnare copy() {
        return new SerumSnare(this);
    }
}

class SerumSnareEffect extends OneShotEffect {

    public SerumSnareEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target nonland permanent to its owner's hand. If that permanent had mana value 3 or less, proliferate. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
    }

    private SerumSnareEffect(final SerumSnareEffect effect) {
        super(effect);
    }

    @Override
    public SerumSnareEffect copy() {
        return new SerumSnareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getFirstTarget());
        if (object == null) {
            return false;
        }
        new ReturnToHandTargetEffect().apply(game, source);
        int manaValue = object.getManaValue();
        if (manaValue <= 3) {
            new ProliferateEffect().apply(game, source);
        }
        return true;
    }
}
