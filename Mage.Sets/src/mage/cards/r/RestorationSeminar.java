package mage.cards.r;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ParadigmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestorationSeminar extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent card from your graveyard");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public RestorationSeminar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{W}");

        this.subtype.add(SubType.LESSON);

        // Return target nonland permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Paradigm
        this.addAbility(new ParadigmAbility());
    }

    private RestorationSeminar(final RestorationSeminar card) {
        super(card);
    }

    @Override
    public RestorationSeminar copy() {
        return new RestorationSeminar(this);
    }
}
