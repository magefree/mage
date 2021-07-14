package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GoblinJavelineer extends CardImpl {

    public GoblinJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Goblin Javelineer becomes blocked, it deals 1 damage to target creature blocking it.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking it");
        filter.add(new BlockingAttackerIdPredicate(this.getId()));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GoblinJavelineer(final GoblinJavelineer card) {
        super(card);
    }

    @Override
    public GoblinJavelineer copy() {
        return new GoblinJavelineer(this);
    }
}
