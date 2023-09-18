package mage.cards.n;

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
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NiblisOfFrost extends CardImpl {

    public NiblisOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever you cast an instant or sorcery spell, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        this.addAbility(ability);
    }

    private NiblisOfFrost(final NiblisOfFrost card) {
        super(card);
    }

    @Override
    public NiblisOfFrost copy() {
        return new NiblisOfFrost(this);
    }
}
