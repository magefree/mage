
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author BetaSteward
 */
public final class EvilTwin extends CardImpl {

    public EvilTwin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Evil Twin enter the battlefield as a copy of any creature on the battlefield, except it has "{U}{B}, {T}: Destroy target creature with the same name as this creature."
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new EvilTwinApplyToPermanent());
        effect.setText("as a copy of any creature on the battlefield, except it has \"{U}{B}, {T}: Destroy target creature with the same name as this creature.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));

    }

    public EvilTwin(final EvilTwin card) {
        super(card);
    }

    @Override
    public EvilTwin copy() {
        return new EvilTwin(this);
    }
}

class EvilTwinApplyToPermanent extends ApplyToPermanent {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with the same name as this creature");

    static {
        filter.add(new EvilTwinPredicate());
    }

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        permanent.addAbility(ability, game);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        mageObject.getAbilities().add(ability);
        return true;
    }

}

class EvilTwinPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        Permanent twin = game.getPermanent(input.getSourceId());
        return permanent != null && twin != null && !twin.getName().isEmpty() && permanent.getName().equals(twin.getName());
    }

    @Override
    public String toString() {
        return "SameNameAsSource";
    }
}
