
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SwiftReckoning extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.instance);
    }

    public SwiftReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may cast Swift Reckoning as though it had flash.
        AsThoughEffect effect = new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame);
        effect.setText("<i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may cast {this} as though it had flash");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalAsThoughEffect(effect,
                SpellMasteryCondition.instance)));
        // Destroy target tapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public SwiftReckoning(final SwiftReckoning card) {
        super(card);
    }

    @Override
    public SwiftReckoning copy() {
        return new SwiftReckoning(this);
    }
}
