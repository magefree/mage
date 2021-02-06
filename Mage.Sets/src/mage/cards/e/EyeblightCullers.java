package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyeblightCullers extends CardImpl {

    public EyeblightCullers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Eyeblight Cullers dies, create three 1/1 green Elf Warrior creature tokens, then mill three cards.
        Ability ability = new DiesSourceTriggeredAbility(new CreateTokenEffect(new ElfWarriorToken(), 3));
        ability.addEffect(new MillCardsControllerEffect(3).concatBy(", then"));
        this.addAbility(ability);
    }

    private EyeblightCullers(final EyeblightCullers card) {
        super(card);
    }

    @Override
    public EyeblightCullers copy() {
        return new EyeblightCullers(this);
    }
}
