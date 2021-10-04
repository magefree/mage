package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SludgeMonster extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SludgeMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Sludge Monster enters the battlefield or attacks, put a slime counter on up to one other target creature.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.SLIME.createInstance())
                        .setText("put a slime counter on up to one other target creature")
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Non-Horror creatures with slime counters on them lose all abilities and have base power and toughness 2/2.
        this.addAbility(new SimpleStaticAbility(new SludgeMonsterEffect()));
    }

    private SludgeMonster(final SludgeMonster card) {
        super(card);
    }

    @Override
    public SludgeMonster copy() {
        return new SludgeMonster(this);
    }
}

class SludgeMonsterEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.SLIME.getPredicate());
        filter.add(Predicates.not(SubType.HORROR.getPredicate()));
    }

    SludgeMonsterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "non-Horror creatures with slime counters on them " +
                "lose all abilities and have base power and toughness 2/2";
    }

    private SludgeMonsterEffect(final SludgeMonsterEffect effect) {
        super(effect);
    }

    @Override
    public SludgeMonsterEffect copy() {
        return new SludgeMonsterEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    permanent.removeAllAbilities(source.getSourceId(), game);
                    return true;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(2);
                        permanent.getToughness().setValue(2);
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
