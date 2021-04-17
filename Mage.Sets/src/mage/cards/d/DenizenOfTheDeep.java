
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class DenizenOfTheDeep extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static{
        filter.add(AnotherPredicate.instance);
    }

    public DenizenOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(11);
        this.toughness = new MageInt(11);

        // When Denizen of the Deep enters the battlefield, return each other creature you control to its owner's hand.
        Effect effect = new ReturnToHandFromBattlefieldAllEffect(filter);
        effect.setText("return each other creature you control to its owner's hand");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private DenizenOfTheDeep(final DenizenOfTheDeep card) {
        super(card);
    }

    @Override
    public DenizenOfTheDeep copy() {
        return new DenizenOfTheDeep(this);
    }
}
