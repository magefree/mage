package mage.cards.m;

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
 * @author LevelX2
 */
public final class MemoryPlunder extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public MemoryPlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/B}{U/B}{U/B}{U/B}");

        // You may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        this.getSpellAbility().addEffect(new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST));
        this.getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

    }

    private MemoryPlunder(final MemoryPlunder card) {
        super(card);
    }

    @Override
    public MemoryPlunder copy() {
        return new MemoryPlunder(this);
    }
}