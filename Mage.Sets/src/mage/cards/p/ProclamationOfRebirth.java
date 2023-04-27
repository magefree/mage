package mage.cards.p;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ProclamationOfRebirth extends CardImpl {

    private static final FilterCreatureCard filter1 = new FilterCreatureCard("creature card with mana value 1 or less from your graveyard");
    private static final FilterCreatureCard filter3 = new FilterCreatureCard("creature cards with mana value 1 or less from your graveyard");

    static {
        filter1.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
        filter3.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public ProclamationOfRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Return up to three target creature cards with converted mana cost 1 or less from your graveyard to the battlefield.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, filter3));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());

        // Forecast - {5}{W}, Reveal Proclamation of Rebirth from your hand: Return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        ForecastAbility ability = new ForecastAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{5}{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter1));
        this.addAbility(ability);
    }

    private ProclamationOfRebirth(final ProclamationOfRebirth card) {
        super(card);
    }

    @Override
    public ProclamationOfRebirth copy() {
        return new ProclamationOfRebirth(this);
    }
}
