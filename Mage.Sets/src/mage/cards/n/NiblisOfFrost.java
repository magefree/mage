
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class NiblisOfFrost extends CardImpl {
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filterCreature.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public NiblisOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever you cast an instant or sorcery spell, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, false);
        ability.addTarget(new TargetCreaturePermanent(filterCreature));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        this.addAbility(ability);
    }

    public NiblisOfFrost(final NiblisOfFrost card) {
        super(card);
    }

    @Override
    public NiblisOfFrost copy() {
        return new NiblisOfFrost(this);
    }
}
