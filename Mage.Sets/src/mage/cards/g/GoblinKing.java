package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GoblinKing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GOBLIN, "Goblins");

    public GoblinKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Goblins get +1/+1 and have mountainwalk.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityAllEffect(
                new MountainwalkAbility(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have mountainwalk"));
        this.addAbility(ability);
    }

    private GoblinKing(final GoblinKing card) {
        super(card);
    }

    @Override
    public GoblinKing copy() {
        return new GoblinKing(this);
    }
}
