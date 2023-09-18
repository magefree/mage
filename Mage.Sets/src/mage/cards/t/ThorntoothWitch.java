
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ThorntoothWitch extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Treefolk spell");

    static {
        filter.add(SubType.TREEFOLK.getPredicate());
    }

    public ThorntoothWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        // Whenever you cast a Treefolk spell, you may have target creature get +3/-3 until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostTargetEffect(3, -3, Duration.EndOfTurn), filter, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ThorntoothWitch(final ThorntoothWitch card) {
        super(card);
    }

    @Override
    public ThorntoothWitch copy() {
        return new ThorntoothWitch(this);
    }
}
