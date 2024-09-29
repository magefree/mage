package mage.cards.a;

import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArcaneHeist extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public ArcaneHeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost. If that spell would be put into their graveyard, exile it instead.
        this.getSpellAbility().addEffect(
                new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true)
                        .setText("You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost. "
                                + "If that spell would be put into their graveyard, exile it instead.")
        );
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private ArcaneHeist(final ArcaneHeist card) {
        super(card);
    }

    @Override
    public ArcaneHeist copy() {
        return new ArcaneHeist(this);
    }
}
