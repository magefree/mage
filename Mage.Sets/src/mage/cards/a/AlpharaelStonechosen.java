package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpharaelStonechosen extends CardImpl {

    public AlpharaelStonechosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward--Discard a card at random.
        this.addAbility(new WardAbility(new DiscardCardCost(true)));

        // Void -- Whenever Alpharael attacks, if a nonland permanent left the battlefield this turn or a spell was warped this turn, defending player loses half their life, rounded up.
        this.addAbility(new AttacksTriggeredAbility(
                new LoseHalfLifeTargetEffect()
                        .setText("defending player loses half their life, rounded up"),
                false, null, SetTargetPointer.PLAYER
        ).withInterveningIf(VoidCondition.instance).setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private AlpharaelStonechosen(final AlpharaelStonechosen card) {
        super(card);
    }

    @Override
    public AlpharaelStonechosen copy() {
        return new AlpharaelStonechosen(this);
    }
}
