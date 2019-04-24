
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToPlayersEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class WisdomOfTheJedi extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("spell with converted mana cost of 3 or less");

    static {
        filterSpell.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public WisdomOfTheJedi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{W}{U}");

        // Choose one - Prevent all damage that would be dealt to players this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToPlayersEffect(Duration.EndOfTurn, false));

        // Target creature you control gets +1/+1 and protection from the color of your choice until end of turn.
        Mode mode = new Mode();
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +1/+1");
        mode.getEffects().add(effect);
        effect = new GainProtectionFromColorTargetEffect(Duration.EndOfTurn);
        effect.setText("and protection from the color of your choice until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // Counter target spell with converted mana cost of 3 or less.
        mode = new Mode();
        mode.getEffects().add(new CounterTargetEffect());
        mode.getTargets().add(new TargetSpell(filterSpell));
        this.getSpellAbility().addMode(mode);

    }

    public WisdomOfTheJedi(final WisdomOfTheJedi card) {
        super(card);
    }

    @Override
    public WisdomOfTheJedi copy() {
        return new WisdomOfTheJedi(this);
    }
}
