package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wavesifter extends CardImpl {

    public Wavesifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wavesifter enters the battlefield, investigate twice.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InvestigateEffect().setText("investigate"));
        ability.addEffect(new InvestigateEffect().setText("twice"));
        this.addAbility(ability);

        // Evoke {G}{U}
        this.addAbility(new EvokeAbility("{G}{U}"));
    }

    private Wavesifter(final Wavesifter card) {
        super(card);
    }

    @Override
    public Wavesifter copy() {
        return new Wavesifter(this);
    }
}
