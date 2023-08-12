package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsSacrificedThisTurnCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.PermanentsSacrificedThisTurnHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TheBalrogDurinsBane extends CardImpl {

    private static final FilterCreaturePermanent filterNonLegendary
        = new FilterCreaturePermanent("except by legendary creatures");

    static {
        filterNonLegendary.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public TheBalrogDurinsBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each permanent sacrificed this turn.
        this.addAbility(
            new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(PermanentsSacrificedThisTurnCount.instance)
                    .setText("this spell costs {1} less to cast for each permanent sacrificed this turn")
            ).addHint(PermanentsSacrificedThisTurnHint.instance).setRuleAtTheTop(true),
            new PermanentsSacrificedWatcher()
        );

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // The Balrog, Durin's Bane can't be blocked except by legendary creatures.
        this.addAbility(new SimpleEvasionAbility((new CantBeBlockedByCreaturesSourceEffect(
            filterNonLegendary, Duration.WhileOnBattlefield
        ))));

        // When The Balrog dies, destroy target artifact or creature an opponent controls.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private TheBalrogDurinsBane(final TheBalrogDurinsBane card) {
        super(card);
    }

    @Override
    public TheBalrogDurinsBane copy() {
        return new TheBalrogDurinsBane(this);
    }
}
