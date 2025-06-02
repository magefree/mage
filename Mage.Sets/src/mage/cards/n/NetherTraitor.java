package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class NetherTraitor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public NetherTraitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Whenever another creature is put into your graveyard from the battlefield, you may pay {B}. If you do, return Nether Traitor from your graveyard to the battlefield.
        this.addAbility(new DiesCreatureTriggeredAbility(Zone.GRAVEYARD, new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{B}")
        ), false, filter, false
        ).setTriggerPhrase("Whenever another creature is put into your graveyard from the battlefield, "));
    }

    private NetherTraitor(final NetherTraitor card) {
        super(card);
    }

    @Override
    public NetherTraitor copy() {
        return new NetherTraitor(this);
    }
}

