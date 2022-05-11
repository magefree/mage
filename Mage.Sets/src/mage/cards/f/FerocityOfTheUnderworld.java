
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Styxo
 */
public final class FerocityOfTheUnderworld extends CardImpl {

    private static final FilterNonlandPermanent filterMode1 = new FilterNonlandPermanent("nonland permanent with mana value 3 or less");

    static {
        filterMode1.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public FerocityOfTheUnderworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{R}{G}");

        // Choose one - Destroy target nonland permanent with converted mana cost 3 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filterMode1));

        // Copy target instant or sorcery spell. You may choose new targets for the copy.
        Mode mode = new Mode(new CopyTargetSpellEffect());
        mode.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().addMode(mode);

        // Return target card from your graveyard to your hand.
        mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard());
        this.getSpellAbility().addMode(mode);
    }

    private FerocityOfTheUnderworld(final FerocityOfTheUnderworld card) {
        super(card);
    }

    @Override
    public FerocityOfTheUnderworld copy() {
        return new FerocityOfTheUnderworld(this);
    }
}
