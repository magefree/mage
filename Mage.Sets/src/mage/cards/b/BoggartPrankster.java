package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoggartPrankster extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN, "attacking Goblin you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public BoggartPrankster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you attack, target attacking Goblin you control gets +1/+0 until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(1, 0), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BoggartPrankster(final BoggartPrankster card) {
        super(card);
    }

    @Override
    public BoggartPrankster copy() {
        return new BoggartPrankster(this);
    }
}
