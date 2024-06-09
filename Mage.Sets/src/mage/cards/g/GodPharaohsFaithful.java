
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author emerald000
 */
public final class GodPharaohsFaithful extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue, black, or red spell");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public GodPharaohsFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever you cast a blue, black or red spell, you gain 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(1), filter, false));
    }

    private GodPharaohsFaithful(final GodPharaohsFaithful card) {
        super(card);
    }

    @Override
    public GodPharaohsFaithful copy() {
        return new GodPharaohsFaithful(this);
    }
}
