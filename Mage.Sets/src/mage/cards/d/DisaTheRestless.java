package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.TarmogoyfToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DisaTheRestless extends CardImpl {

    public DisaTheRestless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Whenever a Lhurgoyf permanent card is put into your graveyard from anywhere other than the battlefield, put it onto the battlefield.
        this.addAbility(new DisaTheRestlessTriggeredAbility());

        // Whenever one or more creatures you control deal combat damage to a player, create a Tarmogoyf token.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new TarmogoyfToken())
        ));
    }

    private DisaTheRestless(final DisaTheRestless card) {
        super(card);
    }

    @Override
    public DisaTheRestless copy() {
        return new DisaTheRestless(this);
    }
}

class DisaTheRestlessTriggeredAbility extends PutCardIntoGraveFromAnywhereAllTriggeredAbility {

    private static final FilterCard filter = new FilterPermanentCard("a Lhurgoyf permanent card");

    static {
        filter.add(SubType.LHURGOYF.getPredicate());
    }

    DisaTheRestlessTriggeredAbility() {
        super(
                new ReturnFromGraveyardToBattlefieldTargetEffect().setText("put it onto the battlefield"),
                false, filter, TargetController.YOU, SetTargetPointer.CARD
        );
    }

    private DisaTheRestlessTriggeredAbility(final DisaTheRestlessTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DisaTheRestlessTriggeredAbility copy() {
        return new DisaTheRestlessTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeEvent) event).getFromZone() != Zone.BATTLEFIELD && super.checkTrigger(event, game);
    }
}