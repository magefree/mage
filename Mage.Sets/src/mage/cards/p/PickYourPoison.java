package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PickYourPoison extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public PickYourPoison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one --
        // * Each opponent sacrifices an artifact.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT));

        // * Each opponent sacrifices an enchantment.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT)));

        // * Each opponent sacrifices a creature with flying.
        this.getSpellAbility().addMode(new Mode(new SacrificeOpponentsEffect(filter)));
    }

    private PickYourPoison(final PickYourPoison card) {
        super(card);
    }

    @Override
    public PickYourPoison copy() {
        return new PickYourPoison(this);
    }
}
