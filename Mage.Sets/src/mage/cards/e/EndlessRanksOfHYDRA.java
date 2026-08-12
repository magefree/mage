package mage.cards.e;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.permanent.token.VillainToken;

/**
 * @author muz
 */
public final class EndlessRanksOfHYDRA extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("your commander");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(CommanderPredicate.instance);
    }

    public EndlessRanksOfHYDRA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // For each opponent, you create a 2/1 black Villain creature token with menace.
        this.getSpellAbility().addEffect(
            new CreateTokenEffect(new VillainToken(), OpponentsCount.instance)
                .setText("for each opponent, you create a 2/1 black Villain creature token with menace")
        );

        // Whenever your commander enters or attacks, you may pay {1}{B}. If you do, return this card from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(
            Zone.GRAVEYARD,
            new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{1}{B}")),
            filter, false
        ));
    }

    private EndlessRanksOfHYDRA(final EndlessRanksOfHYDRA card) {
        super(card);
    }

    @Override
    public EndlessRanksOfHYDRA copy() {
        return new EndlessRanksOfHYDRA(this);
    }
}
