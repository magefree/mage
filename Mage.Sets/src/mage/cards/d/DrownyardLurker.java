package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DrownyardLurker extends CardImpl {

    public DrownyardLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.TRILOBITE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When you cast or cycle Drownyard Lurker, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new OrTriggeredAbility(
                Zone.ALL,
                new CreateTokenEffect(new EldraziSpawnToken()),
                new CastSourceTriggeredAbility(null, false),
                new CycleTriggeredAbility(null, false)
        ));

        // Cycling {2}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private DrownyardLurker(final DrownyardLurker card) {
        super(card);
    }

    @Override
    public DrownyardLurker copy() {
        return new DrownyardLurker(this);
    }
}
