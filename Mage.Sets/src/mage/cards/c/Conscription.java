package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Conscription extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public Conscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        

        // Gain control of target creature with power 2 or less. It becomes a Trooper in addition to its other types.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfGame));
        this.getSpellAbility().addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.TROOPER, false)
                .setText("It becomes a Trooper in addition to its other types"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public Conscription(final Conscription card) {
        super(card);
    }

    @Override
    public Conscription copy() {
        return new Conscription(this);
    }
}
