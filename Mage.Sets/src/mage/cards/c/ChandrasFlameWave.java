package mage.cards.c;

import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandrasFlameWave extends CardImpl {

    private static final FilterCard filter = new FilterCard("Chandra, Flame's Fury");

    static {
        filter.add(new NamePredicate("Chandra, Flame's Fury"));
    }

    public ChandrasFlameWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Chandra's Flame Wave deals 2 damage to target player and each creature that player controls. Search your library and/or graveyard for a card named Chandra, Flame's Fury, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(
                2, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and each creature that player controls."));
        this.getSpellAbility().addEffect(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, false)
        );
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ChandrasFlameWave(final ChandrasFlameWave card) {
        super(card);
    }

    @Override
    public ChandrasFlameWave copy() {
        return new ChandrasFlameWave(this);
    }
}
