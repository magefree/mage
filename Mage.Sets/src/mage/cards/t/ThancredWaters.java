package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThancredWaters extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another target legendary permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ThancredWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Royal Guard -- When Thancred Waters enters, another target legendary permanent you control gains indestructible for as long as you control Thancred Waters.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.WhileControlled)
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Royal Guard"));

        // Whenever you cast a noncreature spell, Thancred Waters gains indestructible until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private ThancredWaters(final ThancredWaters card) {
        super(card);
    }

    @Override
    public ThancredWaters copy() {
        return new ThancredWaters(this);
    }
}
