package mage.cards.u;

import mage.MageInt;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercityScrounger extends CardImpl {

    public UndercityScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: Create a Treasure token. Activate only if a creature died this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()),
                new TapSourceCost(), MorbidCondition.instance
        ).addHint(MorbidHint.instance));
    }

    private UndercityScrounger(final UndercityScrounger card) {
        super(card);
    }

    @Override
    public UndercityScrounger copy() {
        return new UndercityScrounger(this);
    }
}
