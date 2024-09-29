package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.StoneIdolToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientStoneIdol extends CardImpl {

    public AncientStoneIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {1} less to cast for each attacking creature.
        DynamicValue xValue = new AttackingCreatureCount();
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue))
                .addHint(new ValueHint("Attacking creatures", xValue))
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ancient Stone Idol dies, create a 6/12 colorless Construct artifact creature token with trample.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new StoneIdolToken())));
    }

    private AncientStoneIdol(final AncientStoneIdol card) {
        super(card);
    }

    @Override
    public AncientStoneIdol copy() {
        return new AncientStoneIdol(this);
    }
}
