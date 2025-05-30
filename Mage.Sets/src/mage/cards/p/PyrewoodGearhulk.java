package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyrewoodGearhulk extends CardImpl {

    public PyrewoodGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{R}{G}{G}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, other creatures you control get +2/+2 and gain vigilance and menace until end of turn. Damage can't be prevented this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, true
        ).setText("other creatures you control get +2/+2"));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain vigilance"));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and menace until end of turn"));
        ability.addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private PyrewoodGearhulk(final PyrewoodGearhulk card) {
        super(card);
    }

    @Override
    public PyrewoodGearhulk copy() {
        return new PyrewoodGearhulk(this);
    }
}
