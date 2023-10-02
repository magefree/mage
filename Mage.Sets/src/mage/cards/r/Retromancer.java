package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Retromancer extends CardImpl {

    public Retromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Retromancer becomes the target of a spell or ability, Retromancer deals 3 damage to that spell or ability's controller.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new DamageTargetEffect(3)
                .setText("{this} deals 3 damage to that spell or ability's controller"),
                StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.PLAYER, false)
                .withRuleTextReplacement(false));
    }

    private Retromancer(final Retromancer card) {
        super(card);
    }

    @Override
    public Retromancer copy() {
        return new Retromancer(this);
    }
}
