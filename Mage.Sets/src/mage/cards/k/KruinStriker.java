package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class KruinStriker extends CardImpl {

    public KruinStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield under your control, Kruin Striker gets +1/+0 and gains trample until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"),
                StaticFilters.FILTER_ANOTHER_CREATURE);
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn).
                setText("and gains trample until end of turn"));
        this.addAbility(ability);
    }

    private KruinStriker(final KruinStriker card) {
        super(card);
    }

    @Override
    public KruinStriker copy() {
        return new KruinStriker(this);
    }
}
