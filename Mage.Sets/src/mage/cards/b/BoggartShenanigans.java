package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BoggartShenanigans extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BoggartShenanigans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        // Whenever another Goblin you control dies, you may have Boggart Shenanigans deal 1 damage to target player.
        Ability ability = new DiesCreatureTriggeredAbility(
                new DamageTargetEffect(1), true, filter, false
        ).setTriggerPhrase("Whenever another Goblin you control is put into a graveyard from the battlefield, ");
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private BoggartShenanigans(final BoggartShenanigans card) {
        super(card);
    }

    @Override
    public BoggartShenanigans copy() {
        return new BoggartShenanigans(this);
    }
}
