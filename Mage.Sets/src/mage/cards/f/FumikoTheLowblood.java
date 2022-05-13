package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class FumikoTheLowblood extends CardImpl {

    public FumikoTheLowblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fumiko the Lowblood has bushido X, where X is the number of attacking creatures.
        this.addAbility(new BushidoAbility(new AttackingCreatureCount("the number of attacking creatures.")));

        // Creatures your opponents control attack each combat if able.
        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)));
    }

    private FumikoTheLowblood(final FumikoTheLowblood card) {
        super(card);
    }

    @Override
    public FumikoTheLowblood copy() {
        return new FumikoTheLowblood(this);
    }
}
