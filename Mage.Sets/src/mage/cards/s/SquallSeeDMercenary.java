package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquallSeeDMercenary extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SquallSeeDMercenary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Rough Divide -- Whenever a creature you control attacks alone, it gains double strike until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()), true, false
        ).withFlavorWord("Rough Divide"));

        // Whenever Squall deals combat damage to a player, return target permanent card with mana value 3 or less from your graveyard to the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SquallSeeDMercenary(final SquallSeeDMercenary card) {
        super(card);
    }

    @Override
    public SquallSeeDMercenary copy() {
        return new SquallSeeDMercenary(this);
    }
}
