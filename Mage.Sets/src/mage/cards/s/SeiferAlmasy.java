package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeiferAlmasy extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard(
            "instant or sorcery card with mana value 3 or less from your graveyard"
    );

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public SeiferAlmasy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever a creature you control attacks alone, it gains double strike until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())
                        .setText("it gains double strike until end of turn")
        ));

        // Fire Cross -- Whenever Seifer Almasy deals combat damage to a player, you may cast target instant or sorcery card with mana value 3 or less from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true)
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.withFlavorWord("Fire Cross"));
    }

    private SeiferAlmasy(final SeiferAlmasy card) {
        super(card);
    }

    @Override
    public SeiferAlmasy copy() {
        return new SeiferAlmasy(this);
    }
}
