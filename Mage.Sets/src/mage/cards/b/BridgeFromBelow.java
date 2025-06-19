package mage.cards.b;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class BridgeFromBelow extends CardImpl {

    private static final FilterPermanent filter1 = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterCreaturePermanent();

    static {
        filter1.add(TargetController.YOU.getOwnerPredicate());
        filter1.add(TokenPredicate.FALSE);
        filter2.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public BridgeFromBelow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // Whenever a nontoken creature is put into your graveyard from the battlefield, if Bridge from Below is in your graveyard, create a 2/2 black Zombie creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new ZombieToken()),
                false, filter1, false
        ).setTriggerPhrase("Whenever a nontoken creature is put into your graveyard from the battlefield, ")
                .withInterveningIf(SourceInGraveyardCondition.instance));

        // When a creature is put into an opponent's graveyard from the battlefield, if Bridge from Below is in your graveyard, exile Bridge from Below.
        this.addAbility(new DiesCreatureTriggeredAbility(
                Zone.GRAVEYARD, new ExileSourceEffect().setText("exile this card"),
                false, filter2, false
        ).setTriggerPhrase("When a creature is put into an opponent's graveyard from the battlefield, ")
                .withInterveningIf(SourceInGraveyardCondition.instance));
    }

    private BridgeFromBelow(final BridgeFromBelow card) {
        super(card);
    }

    @Override
    public BridgeFromBelow copy() {
        return new BridgeFromBelow(this);
    }
}
