package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrotherhoodPatriarch extends CardImpl {

    public BrotherhoodPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Brotherhood Patriarch dies, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private BrotherhoodPatriarch(final BrotherhoodPatriarch card) {
        super(card);
    }

    @Override
    public BrotherhoodPatriarch copy() {
        return new BrotherhoodPatriarch(this);
    }
}
