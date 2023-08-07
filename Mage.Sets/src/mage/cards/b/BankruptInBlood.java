package mage.cards.b;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BankruptInBlood extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("creatures");

    public BankruptInBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice two creatures.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(2, filter)));

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private BankruptInBlood(final BankruptInBlood card) {
        super(card);
    }

    @Override
    public BankruptInBlood copy() {
        return new BankruptInBlood(this);
    }
}
