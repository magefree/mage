package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangbladeBrigand extends TransformingDoubleFacedCard {

    public FangbladeBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}",
                "Fangblade Eviscerator",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Fangblade Brigand
        this.getLeftHalfCard().setPT(3, 4);

        // {1}{R}: Fangblade Brigand gets +1/+0 and gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this} gets +1/+0"), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Fangblade Eviscerator
        this.getRightHalfCard().setPT(4, 5);

        // {1}{R}: Fangblade Eviscerator gets +1/+0 and gains first strike until end of turn.
        ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this} gets +1/+0"), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getRightHalfCard().addAbility(ability);

        // {4}{R}: Creatures you control get +2/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{4}{R}")));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private FangbladeBrigand(final FangbladeBrigand card) {
        super(card);
    }

    @Override
    public FangbladeBrigand copy() {
        return new FangbladeBrigand(this);
    }
}
