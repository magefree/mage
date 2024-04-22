package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.RatCantBlockToken;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class LordSkitterSewerKing extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.RAT, "another Rat");
    private static final FilterCard filterCard = new FilterCard("card from an opponent's graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public LordSkitterSewerKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Rat enters the battlefield under your control, exile up to one target card from an opponent's graveyard.
        Ability trigger = new EntersBattlefieldControlledTriggeredAbility(
                new ExileTargetEffect(), filter
        );
        trigger.addTarget(new TargetCardInOpponentsGraveyard(0, 1, filterCard));
        this.addAbility(trigger);

        // At the beginning of combat on your turn, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new CreateTokenEffect(new RatCantBlockToken()),
                TargetController.YOU, false
        ));
    }

    private LordSkitterSewerKing(final LordSkitterSewerKing card) {
        super(card);
    }

    @Override
    public LordSkitterSewerKing copy() {
        return new LordSkitterSewerKing(this);
    }
}
