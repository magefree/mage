package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class BalefulBeholder extends CardImpl {

    public BalefulBeholder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.BEHOLDER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When Baleful Beholder enters the battlefield, choose one —
        // • Antimagic Cone — Each opponent sacrifices an enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
        ability.getModes().getMode().withFlavorWord("Antimagic Cone");

        // • Fear Ray — Creatures you control gain menace until end of turn.
        ability.addMode(new Mode(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES))
                .withFlavorWord("Fear Ray"));
        this.addAbility(ability);
    }

    private BalefulBeholder(final BalefulBeholder card) {
        super(card);
    }

    @Override
    public BalefulBeholder copy() {
        return new BalefulBeholder(this);
    }
}
