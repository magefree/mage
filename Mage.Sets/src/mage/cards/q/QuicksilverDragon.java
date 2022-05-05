
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class QuicksilverDragon extends CardImpl {

    public QuicksilverDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}: If target spell has only one target and that target is Quicksilver Dragon, change that spell's target to another creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuicksilverDragonEffect(), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
        
        // Morph {4}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{U}")));
    }

    private QuicksilverDragon(final QuicksilverDragon card) {
        super(card);
    }

    @Override
    public QuicksilverDragon copy() {
        return new QuicksilverDragon(this);
    }
}

class QuicksilverDragonEffect extends OneShotEffect {
    
    QuicksilverDragonEffect() {
        super(Outcome.Benefit);
        this.staticText = "If target spell has only one target and that target is {this}, change that spell's target to another creature";
    }
    
    QuicksilverDragonEffect(final QuicksilverDragonEffect effect) {
        super(effect);
    }
    
    @Override
    public QuicksilverDragonEffect copy() {
        return new QuicksilverDragonEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int numTargets = 0;
            for (Target target : spell.getSpellAbility().getTargets()) {
                numTargets += target.getTargets().size();
            }
            if (numTargets == 1 && spell.getSpellAbility().getTargets().getFirstTarget().equals(source.getSourceId())) {
                spell.chooseNewTargets(game, source.getControllerId(), true, false, null);
            }
            return true;
        }
        return false;
    }
}
