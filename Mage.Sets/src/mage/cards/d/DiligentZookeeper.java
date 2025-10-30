package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiligentZookeeper extends CardImpl {

    public DiligentZookeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Each non-Human creature you control gets +1/+1 for each of its creature types, to a maximum of 10.
        this.addAbility(new SimpleStaticAbility(new DiligentZookeeperEffect()));
    }

    private DiligentZookeeper(final DiligentZookeeper card) {
        super(card);
    }

    @Override
    public DiligentZookeeper copy() {
        return new DiligentZookeeper(this);
    }
}

class DiligentZookeeperEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    DiligentZookeeperEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "each non-Human creature you control gets +1/+1 for each of its creature types, to a maximum of 10";
    }

    private DiligentZookeeperEffect(final DiligentZookeeperEffect effect) {
        super(effect);
    }

    @Override
    public DiligentZookeeperEffect copy() {
        return new DiligentZookeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent.isAllCreatureTypes(game)) {
                permanent.addPower(10);
                permanent.addToughness(10);
                continue;
            }
            int types = Math.min(
                    10, permanent
                            .getSubtype(game)
                            .stream()
                            .map(SubType::getSubTypeSet)
                            .filter(SubTypeSet.CreatureType::equals)
                            .mapToInt(x -> 1)
                            .sum()
            );
            permanent.addPower(types);
            permanent.addToughness(types);
            continue;
        }
        return true;
    }
}
