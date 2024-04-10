package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SicarianInfiltrator extends CardImpl {

    public SicarianInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Squad {2}
        this.addAbility(new SquadAbility(new GenericManaCost(2)));

        // Benediction of Omnissiah -- When Sicarian Infiltrator enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ).withFlavorWord("Benediction of the Omnissiah"));
    }

    private SicarianInfiltrator(final SicarianInfiltrator card) {
        super(card);
    }

    @Override
    public SicarianInfiltrator copy() {
        return new SicarianInfiltrator(this);
    }
}
