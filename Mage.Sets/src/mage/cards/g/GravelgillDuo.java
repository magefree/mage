
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class GravelgillDuo extends CardImpl {

    private static final FilterSpell blueFilter = new FilterSpell("a blue spell");
    private static final FilterSpell blackFilter = new FilterSpell("a black spell");

    static {
        blueFilter.add(new ColorPredicate(ObjectColor.BLUE));
        blackFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public GravelgillDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a blue spell, Gravelgill Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), blueFilter, false));
        // Whenever you cast a black spell, Gravelgill Duo gains fear until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(FearAbility.getInstance(), Duration.EndOfTurn), blackFilter, false));
    }

    private GravelgillDuo(final GravelgillDuo card) {
        super(card);
    }

    @Override
    public GravelgillDuo copy() {
        return new GravelgillDuo(this);
    }
}
