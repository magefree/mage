package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Dreadwurm extends CardImpl {

    public Dreadwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.WURM);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Landfall — Whenever a land enters the battlefield under your control, Dreadwurm gains indestructible until end of turn.
        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        )));
    }

    private Dreadwurm(final Dreadwurm card) {
        super(card);
    }

    @Override
    public Dreadwurm copy() {
        return new Dreadwurm(this);
    }
}
