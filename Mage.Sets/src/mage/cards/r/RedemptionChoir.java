package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RedemptionChoir extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public RedemptionChoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Coven -- Whenever Redemption Choir enters the battlefield or attacks, if you control three or more creatures with different powers, return target permanent card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                        new ReturnFromGraveyardToBattlefieldTargetEffect()
                ), CovenCondition.instance, "Whenever {this} enters the battlefield or attacks, " +
                "if you control three or more creatures with different powers, " +
                "return target permanent card with mana value 3 or less from your graveyard to the battlefield."
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));

    }

    private RedemptionChoir(final RedemptionChoir card) {
        super(card);
    }

    @Override
    public RedemptionChoir copy() {
        return new RedemptionChoir(this);
    }
}
