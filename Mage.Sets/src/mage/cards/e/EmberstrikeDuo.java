
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class EmberstrikeDuo extends CardImpl {

    private static final FilterSpell blackFilter = new FilterSpell("a black spell");
    private static final FilterSpell redFilter = new FilterSpell("a red spell");

    static {
        blackFilter.add(new ColorPredicate(ObjectColor.BLACK));
        redFilter.add(new ColorPredicate(ObjectColor.RED));
    }

    public EmberstrikeDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a black spell, Emberstrike Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), blackFilter, false));
        // Whenever you cast a red spell, Emberstrike Duo gains first strike until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), redFilter, false));
    }

    private EmberstrikeDuo(final EmberstrikeDuo card) {
        super(card);
    }

    @Override
    public EmberstrikeDuo copy() {
        return new EmberstrikeDuo(this);
    }
}
