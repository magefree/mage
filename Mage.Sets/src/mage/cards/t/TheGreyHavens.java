package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorCardInYourGraveyardManaAbility;
import mage.abilities.mana.AnyColorPermanentTypesManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Susucr
 */
public final class TheGreyHavens extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature card");

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
