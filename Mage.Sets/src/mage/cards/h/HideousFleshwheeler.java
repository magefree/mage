package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HideousFleshwheeler extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent card with mana value 2 or less from a graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public HideousFleshwheeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature transforms into Hideous FleshwheelerHideous Fleshwheeler, put target permanent card with mana value 2 or less from a graveyard onto the battlefield under your control.
        Ability ability = new TransformIntoSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private HideousFleshwheeler(final HideousFleshwheeler card) {
        super(card);
    }

    @Override
    public HideousFleshwheeler copy() {
        return new HideousFleshwheeler(this);
    }
}
