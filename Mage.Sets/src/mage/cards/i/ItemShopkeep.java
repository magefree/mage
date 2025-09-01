package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ItemShopkeep extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking equipped creature");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public ItemShopkeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, target attacking equipped creature gains menace until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new GainAbilityTargetEffect(new MenaceAbility(false)), 1
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ItemShopkeep(final ItemShopkeep card) {
        super(card);
    }

    @Override
    public ItemShopkeep copy() {
        return new ItemShopkeep(this);
    }
}
