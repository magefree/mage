
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class UginsConstruct extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("permanent that's one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public UginsConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Ugin's Construct enters the battlefield, sacrifice a permanent that's one or more colors.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(filter, 1, null)));
    }

    private UginsConstruct(final UginsConstruct card) {
        super(card);
    }

    @Override
    public UginsConstruct copy() {
        return new UginsConstruct(this);
    }
}
