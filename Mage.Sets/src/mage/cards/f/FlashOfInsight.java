package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class FlashOfInsight extends CardImpl {

    private static final FilterCard filter = new FilterCard("blue cards from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public FlashOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{U}");

        // Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                GetXValue.instance, 1, PutCards.HAND, PutCards.BOTTOM_ANY));

        // Flashback-{1}{U}, Exile X blue cards from your graveyard.
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new ExileXFromYourGraveCost(filter));
        this.addAbility(ability);
    }

    private FlashOfInsight(final FlashOfInsight card) {
        super(card);
    }

    @Override
    public FlashOfInsight copy() {
        return new FlashOfInsight(this);
    }
}
