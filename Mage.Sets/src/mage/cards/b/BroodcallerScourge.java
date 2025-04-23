package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.OneOrMoreDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroodcallerScourge extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("a permanent card with mana value less than or equal to that damage");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.DRAGON, "Dragons");

    static {
        filter.add(BroodcallerScourgePredicate.instance);
    }

    public BroodcallerScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Dragons you control deal combat damage to a player, you may put a permanent card with mana value less than or equal to that damage from your hand onto the battlefield.
        this.addAbility(new OneOrMoreDamagePlayerTriggeredAbility(
                new PutCardFromHandOntoBattlefieldEffect(filter),
                filter2, true, true
        ));
    }

    private BroodcallerScourge(final BroodcallerScourge card) {
        super(card);
    }

    @Override
    public BroodcallerScourge copy() {
        return new BroodcallerScourge(this);
    }
}

enum BroodcallerScourgePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                <= CardUtil
                .getEffectValueFromAbility(
                        input.getSource(), "damage", Integer.class
                ).orElse(0);
    }
}
