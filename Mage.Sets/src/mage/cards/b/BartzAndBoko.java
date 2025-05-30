package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BartzAndBoko extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.BIRD, "Birds");
    private static final Hint hint = new ValueHint("Birds you control", new PermanentsOnBattlefieldCount(filter));

    public BartzAndBoko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Affinity for Birds
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).setRuleAtTheTop(true).addHint(hint));

        // When Bartz and Boko enters, each other Bird you control deals damage equal to its power to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BartzAndBokoEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BartzAndBoko(final BartzAndBoko card) {
        super(card);
    }

    @Override
    public BartzAndBoko copy() {
        return new BartzAndBoko(this);
    }
}

class BartzAndBokoEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BIRD);

    static {
        filter.add(AnotherPredicate.instance);
    }

    BartzAndBokoEffect() {
        super(Outcome.Benefit);
        staticText = "each other Bird you control deals damage equal to its power to target creature an opponent controls";
    }

    private BartzAndBokoEffect(final BartzAndBokoEffect effect) {
        super(effect);
    }

    @Override
    public BartzAndBokoEffect copy() {
        return new BartzAndBokoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Permanent bird : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.damage(bird.getPower().getValue(), bird.getId(), source, game);
        }
        return true;
    }
}
