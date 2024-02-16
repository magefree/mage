package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GargosViciousWatcher extends CardImpl {

    private static final FilterCard filter = new FilterCard("Hydra spells");

    static {
        filter.add(SubType.HYDRA.getPredicate());
    }

    public GargosViciousWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Hydra spells you cast cost {4} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 4)));

        // Whenever a creature you control becomes the target of a spell, Gargos, Vicious Watcher fights up to one target creature you don't control.
        TriggeredAbility ability = new BecomesTargetAnyTriggeredAbility(new FightTargetSourceEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, StaticFilters.FILTER_SPELL_A,
                SetTargetPointer.NONE, false);
        ability.addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);
    }

    private GargosViciousWatcher(final GargosViciousWatcher card) {
        super(card);
    }

    @Override
    public GargosViciousWatcher copy() {
        return new GargosViciousWatcher(this);
    }
}
