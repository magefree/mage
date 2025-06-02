package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunBlessedHealer extends CardImpl {

    private static final FilterCard filter
            = new FilterNonlandCard("nonland permanent card with mana value 2 or less from your graveyard");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SunBlessedHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, if it was kicked, return target nonland permanent card with mana value 2 or less from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SunBlessedHealer(final SunBlessedHealer card) {
        super(card);
    }

    @Override
    public SunBlessedHealer copy() {
        return new SunBlessedHealer(this);
    }
}
