package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.effects.common.BoostSourcePerpetuallyEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author karapuzz14
 */
public final class BenalishPartisan extends CardImpl {

    public BenalishPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cycle another card, you may pay {1}{W}. If you do, return Benalish Partisan from your graveyard to the battlefield tapped and it perpetually gets +1/+0.
        DoIfCostPaid effect = new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                new ManaCostsImpl<>("{1}{W}"));
        effect.addEffect(new BoostSourcePerpetuallyEffect(1, 0).setText(" and it perpetually gets +1/0"));

        this.addAbility(new CycleControllerTriggeredAbility(Zone.GRAVEYARD, effect, false, true));

        // Cycling {1}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{W}")));

    }

    private BenalishPartisan(final BenalishPartisan card) {
        super(card);
    }

    @Override
    public BenalishPartisan copy() {
        return new BenalishPartisan(this);
    }
}