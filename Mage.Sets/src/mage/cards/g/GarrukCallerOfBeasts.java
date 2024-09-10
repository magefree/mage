
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.command.emblems.GarrukCallerOfBeastsEmblem;

/**
 *
 * @author LevelX2 import mage.game.command.emblems.GarrukCallerOfBeastsEmblem;
 */
public final class GarrukCallerOfBeasts extends CardImpl {

    private static final FilterCreatureCard filterGreenCreature = new FilterCreatureCard("a green creature card");

    static {
        filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public GarrukCallerOfBeasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.setStartingLoyalty(4);

        // +1: Reveal the top 5 cards of your library. Put all creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(5, new FilterCreatureCard("creature cards"), Zone.LIBRARY), 1));

        // -3: You may put a green creature card from your hand onto the battlefield.
        this.addAbility(new LoyaltyAbility(new PutCardFromHandOntoBattlefieldEffect(filterGreenCreature), -3));

        // -7: You get an emblem with "Whenever you cast a creature spell, you may search your library for a creature card, put it onto the battlefield, then shuffle your library.");
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new GarrukCallerOfBeastsEmblem()), -7));

    }

    private GarrukCallerOfBeasts(final GarrukCallerOfBeasts card) {
        super(card);
    }

    @Override
    public GarrukCallerOfBeasts copy() {
        return new GarrukCallerOfBeasts(this);
    }
}