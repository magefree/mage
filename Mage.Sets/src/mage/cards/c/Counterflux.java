
package mage.cards.c;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;


/**
 *
 * @author LevelX2
 */
public final class Counterflux extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public Counterflux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{U}{R}");


        // Counterflux can't be countered.
        Effect effect =  new CantBeCounteredSourceEffect();
        effect.setText("this spell can't be countered");
        Ability ability = new SimpleStaticAbility(Zone.STACK,effect);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Counter target spell you don't control.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        // Overload {1}{U}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new CounterfluxEffect(), new ManaCostsImpl<>("{1}{U}{U}{R}")));
    }

    private Counterflux(final Counterflux card) {
        super(card);
    }

    @Override
    public Counterflux copy() {
        return new Counterflux(this);
    }
}

class CounterfluxEffect extends OneShotEffect {

    public CounterfluxEffect() {
        super(Outcome.Detriment);
        staticText = "Counter each spell you don't control.";

    }

    public CounterfluxEffect(final CounterfluxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Spell> spellsToCounter = new LinkedList<>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell && !stackObject.isControlledBy(source.getControllerId())) {
                spellsToCounter.add((Spell) stackObject);
            }
        }
        for (Spell spell : spellsToCounter) {
            game.getStack().counter(spell.getId(), source, game);
        }
        return true;
    }

    @Override
    public CounterfluxEffect copy() {
        return new CounterfluxEffect(this);
    }

}