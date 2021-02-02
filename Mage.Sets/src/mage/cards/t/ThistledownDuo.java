
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ThistledownDuo extends CardImpl {

    private static final FilterSpell whiteFilter = new FilterSpell("a white spell");
    private static final FilterSpell blueFilter = new FilterSpell("a blue spell");

    static {
        whiteFilter.add(new ColorPredicate(ObjectColor.WHITE));
        blueFilter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ThistledownDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/U}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a white spell, Thistledown Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), whiteFilter, false));
        // Whenever you cast a blue spell, Thistledown Duo gains flying until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), blueFilter, false));
    }

    private ThistledownDuo(final ThistledownDuo card) {
        super(card);
    }

    @Override
    public ThistledownDuo copy() {
        return new ThistledownDuo(this);
    }
}
