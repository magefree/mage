package mage.cards.v;

import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
import mage.game.permanent.token.BlackWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivisPersistence extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("you commander");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(CommanderPredicate.instance);
    }

    public VivisPersistence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Create a 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BlackWizardToken()));

        // Whenever your commander enters or attacks, you may pay {2}. If you do, return this card from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new GenericManaCost(2)),
                filter, false
        ));
    }

    private VivisPersistence(final VivisPersistence card) {
        super(card);
    }

    @Override
    public VivisPersistence copy() {
        return new VivisPersistence(this);
    }
}
