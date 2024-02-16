package mage.cards.s;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SymmetryMatrix extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature with power equal to its toughness");

    static {
        filter.add(SymmetryMatrixPredicate.instance);
    }

    public SymmetryMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a creature with power equal to its toughness enters the battlefield under your control, you may pay {1}. If you do, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        ), filter));
    }

    private SymmetryMatrix(final SymmetryMatrix card) {
        super(card);
    }

    @Override
    public SymmetryMatrix copy() {
        return new SymmetryMatrix(this);
    }
}

enum SymmetryMatrixPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPower().getValue() == input.getToughness().getValue();
    }
}
