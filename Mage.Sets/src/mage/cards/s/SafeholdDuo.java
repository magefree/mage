
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class SafeholdDuo extends CardImpl {

    private static final FilterSpell whiteFilter = new FilterSpell("a white spell");
    private static final FilterSpell greenFilter = new FilterSpell("a green spell");

    static {
        whiteFilter.add(new ColorPredicate(ObjectColor.WHITE));
        greenFilter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SafeholdDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a green spell, Safehold Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), greenFilter, false));
        // Whenever you cast a white spell, Safehold Duo gains vigilance until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn), whiteFilter, false));
    }

    private SafeholdDuo(final SafeholdDuo card) {
        super(card);
    }

    @Override
    public SafeholdDuo copy() {
        return new SafeholdDuo(this);
    }
}
