package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class TruefirePaladin extends CardImpl {

    public TruefirePaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {R}{W}: Truefire Paladin gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}{W}")));

        // {R}{W}: Truefire Paladin gets first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}{W}")));

    }

    private TruefirePaladin(final TruefirePaladin card) {
        super(card);
    }

    @Override
    public TruefirePaladin copy() {
        return new TruefirePaladin(this);
    }
}
