package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.EscapesWithAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CharredGraverobber extends CardImpl {

    private static final FilterCard filter = new FilterCard("outlaw card from your graveyard");

    static {
        filter.add(OutlawPredicate.instance);
    }

    public CharredGraverobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Charred Graverobber enters the battlefield, return target outlaw card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Escape---{3}{B}{B}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}{B}", 4));

        // Charred Graverobber escapes with a +1/+1 counter on it.
        this.addAbility(new EscapesWithAbility(1));
    }

    private CharredGraverobber(final CharredGraverobber card) {
        super(card);
    }

    @Override
    public CharredGraverobber copy() {
        return new CharredGraverobber(this);
    }
}
