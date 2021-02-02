
package mage.cards.p;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Peppersmoke extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("If you control a Faerie,");
    static {
        filter.add(SubType.FAERIE.getPredicate());
    }

    public Peppersmoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{B}");
        this.subtype.add(SubType.FAERIE);


        // Target creature gets -1/-1 until end of turn. If you control a Faerie, draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1,-1,Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter),
                "If you control a Faerie, draw a card"));
    }

    private Peppersmoke(final Peppersmoke card) {
        super(card);
    }

    @Override
    public Peppersmoke copy() {
        return new Peppersmoke(this);
    }
}
