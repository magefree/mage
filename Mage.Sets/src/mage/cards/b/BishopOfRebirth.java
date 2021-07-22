
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TacomenX
 */
public final class BishopOfRebirth extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public BishopOfRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Bishop of Rebirth attacks, you may return target creature card with converted mana cost 3 or less from your graveyard to the battlefield.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("you may return target creature card with mana value 3 or less from your graveyard to the battlefield"), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private BishopOfRebirth(final BishopOfRebirth card) {
        super(card);
    }

    @Override
    public BishopOfRebirth copy() {
        return new BishopOfRebirth(this);
    }
}
