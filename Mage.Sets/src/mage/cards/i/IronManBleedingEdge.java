package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class IronManBleedingEdge extends CardImpl {

    public IronManBleedingEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an artifact spell, you may copy it, except the copy isn't legendary. Do this only once each turn.
        SpellCastControllerTriggeredAbility ability = new SpellCastControllerTriggeredAbility(
            new CopyTargetStackObjectEffect(false, false, false, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY))
                .setText("copy it, except the copy isn't legendary"),
            StaticFilters.FILTER_SPELL_AN_ARTIFACT,
            true,
            SetTargetPointer.SPELL
        );
        ability.setDoOnlyOnceEachTurn(true);
        this.addAbility(ability);
    }

    private IronManBleedingEdge(final IronManBleedingEdge card) {
        super(card);
    }

    @Override
    public IronManBleedingEdge copy() {
        return new IronManBleedingEdge(this);
    }
}
