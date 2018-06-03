
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class AetherShockwave extends CardImpl {

    private static final FilterCreaturePermanent filterSpirit = new FilterCreaturePermanent("Spirits");
    private static final FilterCreaturePermanent filterNonSpirit = new FilterCreaturePermanent("non-Spirit creatures");
    static {
        filterSpirit.add(new SubtypePredicate(SubType.SPIRIT));
        filterNonSpirit.add(Predicates.not(new SubtypePredicate(SubType.SPIRIT)));
    }

    public AetherShockwave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Choose one - Tap all Spirits; or tap all non-Spirit creatures.
        this.getSpellAbility().addEffect(new TapAllEffect(filterSpirit));
        
        Mode mode = new Mode();
        mode.getEffects().add(new TapAllEffect(filterNonSpirit));
        this.getSpellAbility().addMode(mode);
    }

    public AetherShockwave(final AetherShockwave card) {
        super(card);
    }

    @Override
    public AetherShockwave copy() {
        return new AetherShockwave(this);
    }
}
