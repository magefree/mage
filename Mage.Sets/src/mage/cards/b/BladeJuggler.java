package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladeJuggler extends CardImpl {

    public BladeJuggler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Spectacle {2}{B}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{2}{B}")));

        // When Blade Juggler enters the battlefield, it deals 1 damage to you and you draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageControllerEffect(1, "it")
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        this.addAbility(ability);
    }

    private BladeJuggler(final BladeJuggler card) {
        super(card);
    }

    @Override
    public BladeJuggler copy() {
        return new BladeJuggler(this);
    }
}
