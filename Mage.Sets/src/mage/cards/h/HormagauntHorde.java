package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HormagauntHorde extends CardImpl {

    public HormagauntHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Endless Swarm -- Whenever a land enters the battlefield under your control, you may pay {2}{G}. If you do, return Hormagaunt Horde from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToHandEffect(),
                        new ManaCostsImpl<>("{2}{G}")
                ), StaticFilters.FILTER_LAND_A, false
        ).withFlavorWord("Endless Swarm"));
    }

    private HormagauntHorde(final HormagauntHorde card) {
        super(card);
    }

    @Override
    public HormagauntHorde copy() {
        return new HormagauntHorde(this);
    }
}
