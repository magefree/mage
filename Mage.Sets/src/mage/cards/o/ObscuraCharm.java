package mage.cards.o;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObscuraCharm extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("multicolored permanent card with mana value 3 or less from your graveyard");
    private static final FilterPermanent filter2
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker with mana value 3 or less");

    static {
        filter.add(MulticoloredPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ObscuraCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}{B}");

        // Choose one —
        // • Return target multicolored permanent card with mana value 3 or less from your graveyard to the battlefield tapped.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // • Counter target instant or sorcery spell.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect())
                .addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY)));

        // • Destroy target creature or planeswalker with mana value 3 or less.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter2)));
    }

    private ObscuraCharm(final ObscuraCharm card) {
        super(card);
    }

    @Override
    public ObscuraCharm copy() {
        return new ObscuraCharm(this);
    }
}
