
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MasterOfTheVeil extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a morph ability");

    static {
        filter.add(new AbilityPredicate(MorphAbility.class));
    }

    public MasterOfTheVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{U}")));

        // When Master of the Veil is turned face up, you may turn target creature with a morph ability face down.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new MasterOfTheVeilEffect(), false, true);
        ability.addTarget(new TargetCreaturePermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    private MasterOfTheVeil(final MasterOfTheVeil card) {
        super(card);
    }

    @Override
    public MasterOfTheVeil copy() {
        return new MasterOfTheVeil(this);
    }
}

class MasterOfTheVeilEffect extends OneShotEffect {

    MasterOfTheVeilEffect() {
        super(Outcome.Benefit);
        this.staticText = "turn target creature with a morph ability face down";
    }

    MasterOfTheVeilEffect(final MasterOfTheVeilEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfTheVeilEffect copy() {
        return new MasterOfTheVeilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}
