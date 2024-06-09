package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class MaelstromWanderer extends CardImpl {

    public MaelstromWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false)));
        // Cascade
        this.addAbility(new CascadeAbility(false));
        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private MaelstromWanderer(final MaelstromWanderer card) {
        super(card);
    }

    @Override
    public MaelstromWanderer copy() {
        return new MaelstromWanderer(this);
    }
}
