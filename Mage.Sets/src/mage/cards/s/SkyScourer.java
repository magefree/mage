
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class SkyScourer extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a colorless spell");

    static {
        filterSpell.add(ColorlessPredicate.instance);
    }

    public SkyScourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a colorless spell, Sky Scourer gets +1/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), filterSpell, false));

    }

    private SkyScourer(final SkyScourer card) {
        super(card);
    }

    @Override
    public SkyScourer copy() {
        return new SkyScourer(this);
    }
}
