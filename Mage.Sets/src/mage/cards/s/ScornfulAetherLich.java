package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ScornfulAetherLich extends CardImpl {

    public ScornfulAetherLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {W}{B}: Scornful Aether-Lich gains fear and vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FearAbility.getInstance(), Duration.EndOfTurn
        ).setText("{this} gains fear"), new ManaCostsImpl<>("{W}{B}"));
        ability.addEffect(new GainAbilitySourceEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and vigilance until end of turn"));
        this.addAbility(ability);
    }

    private ScornfulAetherLich(final ScornfulAetherLich card) {
        super(card);
    }

    @Override
    public ScornfulAetherLich copy() {
        return new ScornfulAetherLich(this);
    }
}
