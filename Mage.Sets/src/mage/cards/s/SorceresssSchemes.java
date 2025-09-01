package mage.cards.s;

import mage.Mana;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyardOrExile;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SorceresssSchemes extends CardImpl {

    private static final FilterCard filter = new FilterCard("exiled card with flashback you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public SorceresssSchemes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Return target instant or sorcery card from your graveyard or exiled card with flashback you own to your hand. Add {R}.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(
                new TargetCardInYourGraveyardOrExile(
                        StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD, filter
                )
        );
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(1)));

        // Flashback {4}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{R}")));

    }

    private SorceresssSchemes(final SorceresssSchemes card) {
        super(card);
    }

    @Override
    public SorceresssSchemes copy() {
        return new SorceresssSchemes(this);
    }
}
