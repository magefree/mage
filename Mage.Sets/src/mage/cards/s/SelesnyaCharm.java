
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SelesnyaCharm extends CardImpl {

    static private final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 5 or greater");
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public SelesnyaCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}");

       
        // Choose one — Target creature gets +2/+2 and gains trample until end of turn;
        Effect effect = new BoostTargetEffect(2,2, Duration.EndOfTurn);
        effect.setText("Target creature gets +2/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().getTargets().add(new TargetCreaturePermanent());

        // or exile target creature with power 5 or greater;
        Mode mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);

        // or create a 2/2 white Knight creature token with vigilance.
        mode = new Mode(new CreateTokenEffect(new KnightToken()));
        this.getSpellAbility().addMode(mode);
    }

    private SelesnyaCharm(final SelesnyaCharm card) {
        super(card);
    }

    @Override
    public SelesnyaCharm copy() {
        return new SelesnyaCharm(this);
    }
}
