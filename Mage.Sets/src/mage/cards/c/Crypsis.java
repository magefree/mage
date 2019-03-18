
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Crypsis extends CardImpl {

    static final FilterCard filter = new FilterCard("creatures your opponents control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public Crypsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Target creature you control gains protection from creatures your opponents control until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap it.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        
    }

    public Crypsis(final Crypsis card) {
        super(card);
    }

    @Override
    public Crypsis copy() {
        return new Crypsis(this);
    }
}
