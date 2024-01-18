package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassacreGirlKnownKiller extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("a creature an opponent controls");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 1));
    }

    public MassacreGirlKnownKiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Creatures you control have wither.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                WitherAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Whenever a creature an opponent controls dies, if its toughness was less than 1, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("if its toughness was less than 1, draw a card"),
                false, filter
        ));
    }

    private MassacreGirlKnownKiller(final MassacreGirlKnownKiller card) {
        super(card);
    }

    @Override
    public MassacreGirlKnownKiller copy() {
        return new MassacreGirlKnownKiller(this);
    }
}
