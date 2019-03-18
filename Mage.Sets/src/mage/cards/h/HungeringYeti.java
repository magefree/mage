
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class HungeringYeti extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("as long as you control a green or blue permanent");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.BLUE)));
    }

    public HungeringYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as you control a green or blue permanent, you may cast Hungering Yeti as though it had flash.
        AsThoughEffect effect = new CastAsThoughItHadFlashSourceEffect(Duration.EndOfGame);
        effect.setText("As long as you control a green or blue permanent, you may cast {this} as though it had flash");
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalAsThoughEffect(effect,
                        new PermanentsOnTheBattlefieldCondition(filter))));

    }

    public HungeringYeti(final HungeringYeti card) {
        super(card);
    }

    @Override
    public HungeringYeti copy() {
        return new HungeringYeti(this);
    }
}
