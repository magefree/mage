package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResurgentBelief extends CardImpl {

    public ResurgentBelief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setWhite(true);

        // Suspend 2—{1}{W}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{1}{W}"), this));

        // Return all enchantment cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_ENCHANTMENTS));
    }

    private ResurgentBelief(final ResurgentBelief card) {
        super(card);
    }

    @Override
    public ResurgentBelief copy() {
        return new ResurgentBelief(this);
    }
}
