package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KnowledgeAndPower extends CardImpl {

    public KnowledgeAndPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // Whenever you scry, you may pay {2}. If you do, Knowledge and Power deals 2 damage to any target.
        Ability ability = new ScryTriggeredAbility(
                new DoIfCostPaid(new DamageTargetEffect(2), new GenericManaCost(2))
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private KnowledgeAndPower(final KnowledgeAndPower card) {
        super(card);
    }

    @Override
    public KnowledgeAndPower copy() {
        return new KnowledgeAndPower(this);
    }
}
