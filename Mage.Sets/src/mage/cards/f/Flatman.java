package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class Flatman extends CardImpl {

    public Flatman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(10);

        // Origami-Fu -- {2}{G}: Switch Flatman's power and toughness until end of turn.
        this.addAbility(new SimpleActivatedAbility(
            new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{2}{G}")
        ).withFlavorWord("Origami-Fu"));
    }

    private Flatman(final Flatman card) {
        super(card);
    }

    @Override
    public Flatman copy() {
        return new Flatman(this);
    }
}
