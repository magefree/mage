package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightfootRogue extends CardImpl {

    public LightfootRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sneak Attack — Whenever Lightfoot Rogue attacks, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new AttacksTriggeredAbility(effect, false).withFlavorWord("Sneak Attack"));

        // 1-9 | Lightfoot Rogue gains deathtouch until end of turn.
        effect.addTableEntry(
                1, 9,
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(), Duration.EndOfTurn
                )
        );

        // 10-19 | It gets +1/+0 and gains deathtouch until end of turn.
        effect.addTableEntry(
                10, 19,
                new BoostSourceEffect(
                        1, 0, Duration.EndOfTurn
                ).setText("it gets +1/+0"),
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(), Duration.EndOfTurn
                ).setText("and gains deathtouch until end of turn")
        );

        // 20 | It gets +3/+0 and gains first strike and deathtouch until end of turn.
        effect.addTableEntry(
                20, 20,
                new BoostSourceEffect(
                        3, 0, Duration.EndOfTurn
                ).setText("it gets +3/+0"),
                new GainAbilitySourceEffect(
                        FirstStrikeAbility.getInstance(), Duration.EndOfTurn
                ).setText("and gains first strike"),
                new GainAbilitySourceEffect(
                        DeathtouchAbility.getInstance(), Duration.EndOfTurn
                ).setText("and deathtouch until end of turn")
        );
    }

    private LightfootRogue(final LightfootRogue card) {
        super(card);
    }

    @Override
    public LightfootRogue copy() {
        return new LightfootRogue(this);
    }
}
