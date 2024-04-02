package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
 */
public final class DreadhordeArcanist extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value less than or equal to {this}'s power from your graveyard"
    );

    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
    }

    public DreadhordeArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Dreadhorde Arcanist attacks, you may cast target instant or sorcery card with converted mana cost less than or equal to Dreadhorde Arcanist's power from your graveyard without paying its mana cost.
        // If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new AttacksTriggeredAbility(new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private DreadhordeArcanist(final DreadhordeArcanist card) {
        super(card);
    }

    @Override
    public DreadhordeArcanist copy() {
        return new DreadhordeArcanist(this);
    }
}
