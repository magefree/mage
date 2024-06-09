package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentEnteringBattlefieldManaValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author bobby-mccann
 */
public final class RadagastTheBrown extends CardImpl {

    static final FilterCreatureCard cardFilter = new FilterCreatureCard("creature card that doesn't share a creature type with a creature you control");

    static {
        cardFilter.add(RadagastTheBrownPredicate.instance);
    }

    public RadagastTheBrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Radagast the Brown or another nontoken creature enters the battlefield under your control, look at the top X cards of your library, where X is that creature's mana value. You may reveal a creature card from among them that doesn't share a creature type with a creature you control and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        PermanentEnteringBattlefieldManaValue.instance, 1,
                        cardFilter,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ),
                StaticFilters.FILTER_CREATURE_NON_TOKEN,
                false,
                true
        ));
    }

    private RadagastTheBrown(final RadagastTheBrown card) {
        super(card);
    }

    @Override
    public RadagastTheBrown copy() {
        return new RadagastTheBrown(this);
    }
}

enum RadagastTheBrownPredicate implements Predicate<Card> {
    instance;
    public boolean apply(Card card, Game game) {
        UUID playerId = card.getOwnerId();
        List<Permanent> creaturesYouControl = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                playerId,
                game
        );
        for (Permanent creature : creaturesYouControl) {
            if (creature.shareCreatureTypes(game, card)) {
                return false;
            }
        }
        return true;
    }
}