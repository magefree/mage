
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class MirranSpy extends CardImpl {
    
    public MirranSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an artifact spell, you may untap target creature.
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(new UntapTargetEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MirranSpy(final MirranSpy card) {
        super(card);
    }

    @Override
    public MirranSpy copy() {
        return new MirranSpy(this);
    }
}
