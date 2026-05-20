package mage.cards.e;

import mage.MageInt;
import mage.abilities.abilityword.OpusAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElementalMascot extends CardImpl {

    public ElementalMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Opus -- Whenever you cast an instant or sorcery spell, this creature gets +1/+0 until end of turn. If five or more mana was spent to cast that spell, exile the top card of your library. You may play that card until the end of your next turn.
        this.addAbility(new OpusAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
                        .withTextOptions("that card", true), null, false
        ));
    }

    private ElementalMascot(final ElementalMascot card) {
        super(card);
    }

    @Override
    public ElementalMascot copy() {
        return new ElementalMascot(this);
    }
}
