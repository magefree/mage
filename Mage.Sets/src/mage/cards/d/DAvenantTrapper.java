
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DAvenantTrapper extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a historic spell");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public DAvenantTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a historic spell, tap target creature an opponent controls.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new TapTargetEffect("tap target creature an opponent controls. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                filter, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private DAvenantTrapper(final DAvenantTrapper card) {
        super(card);
    }

    @Override
    public DAvenantTrapper copy() {
        return new DAvenantTrapper(this);
    }
}
