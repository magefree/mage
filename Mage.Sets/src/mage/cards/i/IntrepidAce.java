package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author Codex
 */
public final class IntrepidAce extends CardImpl {

    public IntrepidAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // This creature gets +2/+0 as long as it isn't attacking or blocking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                new InvertCondition(new SourceMatchesFilterCondition(new FilterAttackingOrBlockingCreature())),
                "{this} gets +2/+0 as long as it isn't attacking or blocking"
        )));
    }

    private IntrepidAce(final IntrepidAce card) {
        super(card);
    }

    @Override
    public IntrepidAce copy() {
        return new IntrepidAce(this);
    }
}
