package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GastalRaider extends CardImpl {

    public GastalRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // When this creature enters, target opponent reveals their hand. You choose an instant or sorcery card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Max speed -- This creature gets +1/+1 and has menace.
        ability = new SimpleStaticAbility(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility(false)).setText("and has menace"));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private GastalRaider(final GastalRaider card) {
        super(card);
    }

    @Override
    public GastalRaider copy() {
        return new GastalRaider(this);
    }
}
