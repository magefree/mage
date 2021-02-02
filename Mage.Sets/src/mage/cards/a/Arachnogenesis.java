
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.token.SpiderToken;

/**
 *
 * @author fireshoes
 */
public final class Arachnogenesis extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Spider creatures");

    static {
        filter.add(Predicates.not(SubType.SPIDER.getPredicate()));
    }

    public Arachnogenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // create X 1/2 green Spider creature tokens with reach, where X is the number of creatures attacking you.
        Effect effect = new CreateTokenEffect(new SpiderToken(), new ArachnogenesisCount());
        effect.setText("create X 1/2 green Spider creature tokens with reach, where X is the number of creatures attacking you");
        this.getSpellAbility().addEffect(effect);
        
        // Prevent all combat damage that would be dealt this turn by non-Spider creatures.
         this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    private Arachnogenesis(final Arachnogenesis card) {
        super(card);
    }

    @Override
    public Arachnogenesis copy() {
        return new Arachnogenesis(this);
    }
}

class ArachnogenesisCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getDefenderId().equals(sourceAbility.getControllerId())) {
                count += combatGroup.getAttackers().size();
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creatures attacking you";
    }
}