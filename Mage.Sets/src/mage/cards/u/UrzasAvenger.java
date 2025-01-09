package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Styxo & L_J
 */
public final class UrzasAvenger extends CardImpl {

    public UrzasAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Urza's Avenger gets -1/-1 and gains your choice of banding, flying, first strike, or trample until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(-1, -1, Duration.EndOfTurn)
                .setText("{this} gets -1/-1"), new ManaCostsImpl<>("{0}"));
        ability.addEffect(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source, "", true,
                BandingAbility.getInstance(), FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), TrampleAbility.getInstance())
                .concatBy("and"));
        this.addAbility(ability);
    }

    private UrzasAvenger(final UrzasAvenger card) {
        super(card);
    }

    @Override
    public UrzasAvenger copy() {
        return new UrzasAvenger(this);
    }
}
