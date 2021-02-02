
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
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
public final class TattermungeDuo extends CardImpl {

    private static final FilterSpell redFilter = new FilterSpell("a red spell");
    private static final FilterSpell greenFilter = new FilterSpell("a green spell");

    static {
        redFilter.add(new ColorPredicate(ObjectColor.RED));
        greenFilter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public TattermungeDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast a red spell, Tattermunge Duo gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), redFilter, false));
        // Whenever you cast a green spell, Tattermunge Duo gains forestwalk until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainAbilitySourceEffect(new ForestwalkAbility(false),
                Duration.EndOfTurn), greenFilter, false));
    }

    private TattermungeDuo(final TattermungeDuo card) {
        super(card);
    }

    @Override
    public TattermungeDuo copy() {
        return new TattermungeDuo(this);
    }
}
