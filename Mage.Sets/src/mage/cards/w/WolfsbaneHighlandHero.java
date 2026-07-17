package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WolfsbaneHighlandHero extends CardImpl {

    public WolfsbaneHighlandHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {2}{G}: Wolfsbane gets +2/+2 until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
            Zone.BATTLEFIELD,
            new BoostSourceEffect(2, 2, Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}{G}")
        ));
    }

    private WolfsbaneHighlandHero(final WolfsbaneHighlandHero card) {
        super(card);
    }

    @Override
    public WolfsbaneHighlandHero copy() {
        return new WolfsbaneHighlandHero(this);
    }
}
