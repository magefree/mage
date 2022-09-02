package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author weirddan455
 */
public final class HaughtyDjinn extends CardImpl {

    private static final FilterInstantOrSorceryCard filter = new FilterInstantOrSorceryCard("instant and sorcery cards");
    private static final FilterInstantOrSorceryCard filter2 = new FilterInstantOrSorceryCard("instant and sorcery spells");

    public HaughtyDjinn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haughty Djinn's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(new CardsInControllerGraveyardCount(filter), Duration.EndOfGame)
        ));

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter2, 1)));
    }

    private HaughtyDjinn(final HaughtyDjinn card) {
        super(card);
    }

    @Override
    public HaughtyDjinn copy() {
        return new HaughtyDjinn(this);
    }
}
