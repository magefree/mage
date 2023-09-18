
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WarpingWail extends CardImpl {

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature with power or toughness 1 or less");
    private static final FilterSpell filterSorcery = new FilterSpell("sorcery spell");

    static {
        filterCreature.add(Predicates.or(
                new PowerPredicate(ComparisonType.FEWER_THAN, 2),
                new ToughnessPredicate(ComparisonType.FEWER_THAN, 2)));
        filterSorcery.add(CardType.SORCERY.getPredicate());
    }

    public WarpingWail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{C}");

        // Choose one &mdash; Exile target creature with power or toughness 1 or less.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile target creature with power or toughness 1 or less.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterCreature));

        // Counter target sorcery spell.
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetSpell(filterSorcery));
        this.getSpellAbility().addMode(mode);

        // Create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        effect = new CreateTokenEffect(new EldraziScionToken());
        effect.setText("Create a 1/1 colorless Eldrazi Scion creature token. It has \"Sacrifice this creature: Add {C}.\"");
        this.getSpellAbility().addMode(new Mode(effect));
    }

    private WarpingWail(final WarpingWail card) {
        super(card);
    }

    @Override
    public WarpingWail copy() {
        return new WarpingWail(this);
    }
}
