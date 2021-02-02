
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class KozileksSentinel extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a colorless spell");

    static {
        filterSpell.add(ColorlessPredicate.instance);
    }

    public KozileksSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Whenever you cast a colorless spell, Kozilek's Sentinel gets +1/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), filterSpell, false));

    }

    private KozileksSentinel(final KozileksSentinel card) {
        super(card);
    }

    @Override
    public KozileksSentinel copy() {
        return new KozileksSentinel(this);
    }
}
