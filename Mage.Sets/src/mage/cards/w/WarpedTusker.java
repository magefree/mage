package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class WarpedTusker extends CardImpl {

    public WarpedTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When you cast or cycle Warped Tusker, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new OrTriggeredAbility(
                Zone.ALL,
                new CreateTokenEffect(new EldraziSpawnToken()),
                new CastSourceTriggeredAbility(null, false),
                new CycleTriggeredAbility(null, false)
        ).setTriggerPhrase("When you cast or cycle {this}, "));

        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}")));

    }

    private WarpedTusker(final WarpedTusker card) {
        super(card);
    }

    @Override
    public WarpedTusker copy() {
        return new WarpedTusker(this);
    }
}
