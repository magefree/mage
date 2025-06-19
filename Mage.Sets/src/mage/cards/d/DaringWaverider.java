package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class DaringWaverider extends CardImpl {
    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 4 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
    }

    public DaringWaverider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Daring Waverider enters, you may cast target instant or sorcery card with mana value 4 or less from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private DaringWaverider(final DaringWaverider card) {
        super(card);
    }

    @Override
    public DaringWaverider copy() {
        return new DaringWaverider(this);
    }
}
