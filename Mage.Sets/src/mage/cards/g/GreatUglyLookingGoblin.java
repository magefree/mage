package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class GreatUglyLookingGoblin extends AdventureCard {

    public GreatUglyLookingGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{B}", "Clap! Snap!", "{1}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Each creature you control with a +1/+1 counter on it has menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            new MenaceAbility(),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_EACH_CONTROLLED_CREATURE_P1P1,
            "Each creature you control with a +1/+1 counter on it has menace. " +
                "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"
        )));

        // Clap! Snap!
        // Amass Goblins 2.
        this.getSpellCard().getSpellAbility().addEffect(new AmassEffect(2, SubType.GOBLIN));

        this.finalizeAdventure();
    }

    private GreatUglyLookingGoblin(final GreatUglyLookingGoblin card) {
        super(card);
    }

    @Override
    public GreatUglyLookingGoblin copy() {
        return new GreatUglyLookingGoblin(this);
    }
}
