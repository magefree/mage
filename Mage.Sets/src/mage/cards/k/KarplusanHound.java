package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KarplusanHound extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPlaneswalkerPermanent(SubType.CHANDRA, "you control a Chandra planeswalker")
    );

    public KarplusanHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Karplusan Hound attacks, if you control a Chandra planeswalker, this creature deals 2 damage to any target.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(2)).withInterveningIf(condition);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private KarplusanHound(final KarplusanHound card) {
        super(card);
    }

    @Override
    public KarplusanHound copy() {
        return new KarplusanHound(this);
    }
}
