package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorCardInYourGraveyardManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TheGreyHavens extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature cards");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheGreyHavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.supertype.add(SuperType.LEGENDARY);

        // When The Grey Havens enters the battlefield, scry 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(1, false)));
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {T}: Add one mana of any color among legendary creature cards in your graveyard.
        this.addAbility(new AnyColorCardInYourGraveyardManaAbility(filter));
    }

    private TheGreyHavens(final TheGreyHavens card) {
        super(card);
    }

    @Override
    public TheGreyHavens copy() {
        return new TheGreyHavens(this);
    }
}
