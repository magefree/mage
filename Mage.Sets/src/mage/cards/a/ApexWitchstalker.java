package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ApexWitchstalker extends CardImpl {

    public ApexWitchstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters or dies, you gain 2 life.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new GainLifeEffect(2), false));

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ApexWitchstalker(final ApexWitchstalker card) {
        super(card);
    }

    @Override
    public ApexWitchstalker copy() {
        return new ApexWitchstalker(this);
    }
}
