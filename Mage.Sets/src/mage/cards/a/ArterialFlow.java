
package mage.cards.a;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author L_J
 */
public final class ArterialFlow extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("If you control a Vampire,");
    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public ArterialFlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Each opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(StaticValue.get(2), false, TargetController.OPPONENT));
        // If you control a Vampire, each opponent loses 2 life and you gain 2 life.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new LoseLifeOpponentsEffect(2), new PermanentsOnTheBattlefieldCondition(filter), 
                "If you control a Vampire, each opponent loses 2 life"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(2), new PermanentsOnTheBattlefieldCondition(filter), "and you gain 2 life"));
    }

    private ArterialFlow(final ArterialFlow card) {
        super(card);
    }

    @Override
    public ArterialFlow copy() {
        return new ArterialFlow(this);
    }
}
