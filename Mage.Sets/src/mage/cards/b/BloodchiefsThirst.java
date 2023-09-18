package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodchiefsThirst extends CardImpl {

    public BloodchiefsThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Kicker {2}{B}
        this.addAbility(new KickerAbility("{2}{B}"));

        // Destroy target creature or planeswalker with converted mana cost 2 or less. If this spell was kicked, instead destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(
                "Destroy target creature or planeswalker with mana value 2 or less. " +
                        "If this spell was kicked, instead destroy target creature or planeswalker."
        ));
        this.getSpellAbility().setTargetAdjuster(BloodchiefsThirstAdjuster.instance);
    }

    private BloodchiefsThirst(final BloodchiefsThirst card) {
        super(card);
    }

    @Override
    public BloodchiefsThirst copy() {
        return new BloodchiefsThirst(this);
    }
}

enum BloodchiefsThirstAdjuster implements TargetAdjuster {
    instance;

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker with mana value 2 or less"
    );

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetCreatureOrPlaneswalker());
        } else {
            ability.addTarget(new TargetPermanent(filter));
        }
    }
}
