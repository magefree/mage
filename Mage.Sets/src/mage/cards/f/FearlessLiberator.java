package mage.cards.f;

import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DwarfBerserkerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearlessLiberator extends CardImpl {

    public FearlessLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Boast — {2}{R}: Create a 2/1 red Dwarf Berserker creature token.
        this.addAbility(new BoastAbility(new CreateTokenEffect(new DwarfBerserkerToken()), "{2}{R}"));
    }

    private FearlessLiberator(final FearlessLiberator card) {
        super(card);
    }

    @Override
    public FearlessLiberator copy() {
        return new FearlessLiberator(this);
    }
}
