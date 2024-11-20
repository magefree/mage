package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberwildeCaliph extends CardImpl {

    public EmberwildeCaliph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Emberwilde Caliph attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Emberwilde Caliph deals damage, you lose that much life.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new LoseLifeSourceControllerEffect(SavedDamageValue.MUCH)));
    }

    private EmberwildeCaliph(final EmberwildeCaliph card) {
        super(card);
    }

    @Override
    public EmberwildeCaliph copy() {
        return new EmberwildeCaliph(this);
    }
}
