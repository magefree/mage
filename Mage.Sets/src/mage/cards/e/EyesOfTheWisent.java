package mage.cards.e;

import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.EyesOfTheWisentElementalToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EyesOfTheWisent extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public EyesOfTheWisent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        // Whenever an opponent casts a blue spell during your turn, you may create a 4/4 green Elemental creature token.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new EyesOfTheWisentElementalToken()), filter, true),
                MyTurnCondition.instance,
                "Whenever an opponent casts a blue spell during your turn, you may create a 4/4 green Elemental creature token."
        ).addHint(MyTurnHint.instance));
    }

    private EyesOfTheWisent(final EyesOfTheWisent card) {
        super(card);
    }

    @Override
    public EyesOfTheWisent copy() {
        return new EyesOfTheWisent(this);
    }
}
