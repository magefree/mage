package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author muz
 */
public final class OwnTheDay extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that attacked this turn");

    static {
        filter.add(AttackedThisTurnPredicate.instance);
    }

    public OwnTheDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");


        // Battlefront -- This spell costs {1} less to cast for each opponent you attacked this turn.
        this.addAbility(
            new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, AttackedThisTurnOpponentsCount.instance)
                    .setText("this spell costs {1} less to cast for each opponent you attacked this turn")
            ).withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint()),
            new PlayersAttackedThisTurnWatcher()
        );

        // Untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapAllEffect(filter));
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());
    }

    private OwnTheDay(final OwnTheDay card) {
        super(card);
    }

    @Override
    public OwnTheDay copy() {
        return new OwnTheDay(this);
    }
}
