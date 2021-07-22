
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SunTitan extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SunTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Sun Titan enters the battlefield or attacks, you may return target permanent card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SunTitan(final SunTitan card) {
        super(card);
    }

    @Override
    public SunTitan copy() {
        return new SunTitan(this);
    }

}
