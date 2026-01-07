package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HexingSquelcher extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("creature spells you control");

    public HexingSquelcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2)));

        // Spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(filter, Duration.WhileOnBattlefield)));

        // Other creatures you control have "Ward--Pay 2 life."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new PayLifeCost(2)), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));
    }

    private HexingSquelcher(final HexingSquelcher card) {
        super(card);
    }

    @Override
    public HexingSquelcher copy() {
        return new HexingSquelcher(this);
    }
}
