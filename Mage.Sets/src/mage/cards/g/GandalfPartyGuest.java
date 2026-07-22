package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GandalfPartyGuest extends CardImpl {

    private static final FilterCard filterCard = new FilterInstantOrSorceryCard(
        "an instant or sorcery spell with mana value X or less"
    );

    static {
        filterCard.add(GandalfPartyGuestPredicate.instance);
    }

    public GandalfPartyGuest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, you may cast an instant or sorcery spell with mana value X or less from your hand without paying its mana cost, where X is twice the number of legendary Wizards you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new CastFromHandForFreeEffect(filterCard)
            .setText("you may cast an instant or sorcery spell with mana value X or less from your hand "
            + "without paying its mana cost, where X is twice the number of legendary Wizards you control"));
        this.addAbility(ability);
    }

    private GandalfPartyGuest(final GandalfPartyGuest card) {
        super(card);
    }

    @Override
    public GandalfPartyGuest copy() {
        return new GandalfPartyGuest(this);
    }
}

enum GandalfPartyGuestPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    private static final FilterPermanent filter = new FilterControlledPermanent("legendary Wizards you control");
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SubType.WIZARD.getPredicate());
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        int value = 2 * game.getBattlefield().countAll(filter, input.getPlayerId(), game);
        return input.getObject().getManaValue() <= value;
    }
}
