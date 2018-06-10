
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class EmmaraTandris extends CardImpl {

    private static final FilterCreatureOrPlayer filter = new FilterCreatureOrPlayer("creature tokens you control");
    static {
        filter.getCreatureFilter().add(new TokenPredicate());
        filter.getCreatureFilter().add(new ControllerPredicate(TargetController.YOU));
        filter.getPlayerFilter().add(new PlayerIdPredicate(UUID.randomUUID()));
    }

    public EmmaraTandris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Prevent all damage that would be dealt to creature tokens you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    public EmmaraTandris(final EmmaraTandris card) {
        super(card);
    }

    @Override
    public EmmaraTandris copy() {
        return new EmmaraTandris(this);
    }
}
