package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OgnisTheDragonsLash extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with haste");

    static {
        filter.add(new AbilityPredicate(HasteAbility.class));
    }

    public OgnisTheDragonsLash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{R}{R/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a creature you control with haste attacks, create a tapped Treasure token.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true, false), false, filter
        ));
    }

    private OgnisTheDragonsLash(final OgnisTheDragonsLash card) {
        super(card);
    }

    @Override
    public OgnisTheDragonsLash copy() {
        return new OgnisTheDragonsLash(this);
    }
}
