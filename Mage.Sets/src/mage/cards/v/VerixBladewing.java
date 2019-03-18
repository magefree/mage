package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.KaroxBladewingDragonToken;

/**
 * @author JRHerlehy
 *         Created on 4/5/18.
 */
public final class VerixBladewing extends CardImpl {

    public VerixBladewing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Verix Bladewing enters the battlefield, if it was kicked, create Karox Bladewing,
        //  a legendary 4/4 red Dragon creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new KaroxBladewingDragonToken()), KickedCondition.instance,
                "create Karox Bladewing, a legendary 4/4 red Dragon creature token with flying.")));
    }

    public VerixBladewing(final VerixBladewing card) {
        super(card);
    }

    @Override
    public VerixBladewing copy() {
        return new VerixBladewing(this);
    }
}
