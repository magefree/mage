package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronCrawCrusher extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public IronCrawCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Prototype {2}{G}{G} -- 2/5
        this.addAbility(new PrototypeAbility(this, "{2}{G}{G}", 2, 5));

        // Whenever Iron-Craw Crusher attacks, target attacking creature gets +X/+0 until end of turn, where X is Iron-Craw Crusher's power.
        Ability ability = new AttacksTriggeredAbility(
                new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn)
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private IronCrawCrusher(final IronCrawCrusher card) {
        super(card);
    }

    @Override
    public IronCrawCrusher copy() {
        return new IronCrawCrusher(this);
    }
}
