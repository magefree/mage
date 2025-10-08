
package mage.cards.j;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class JodahsAvenger extends CardImpl {

    public JodahsAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {0}: Until end of turn, Jodah's Avenger gets -1/-1 and gains your choice of double strike, protection from red, vigilance, or shadow.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(-1, -1, Duration.EndOfTurn)
                .setText("Until end of turn, {this} gets -1/-1"), new ManaCostsImpl<>("{0}"));
        ability.addEffect(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source, "", false,
                DoubleStrikeAbility.getInstance(), ProtectionAbility.from(ObjectColor.RED), VigilanceAbility.getInstance(), ShadowAbility.getInstance())
                .concatBy("and"));
        this.addAbility(ability);
    }

    private JodahsAvenger(final JodahsAvenger card) {
        super(card);
    }

    @Override
    public JodahsAvenger copy() {
        return new JodahsAvenger(this);
    }
}
