
package mage.cards.s;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SilumgarTheDriftingDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon you control");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SilumgarTheDriftingDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        // Whenever a Dragon you control attacks, creatures defending player controls get -1/-1 until end of turn.
        this.addAbility(
                new AttacksAllTriggeredAbility(
                        new UnboostCreaturesTargetPlayerEffect(-1, -1),
                        false, filter, SetTargetPointer.PLAYER, false));
    }

    private SilumgarTheDriftingDeath(final SilumgarTheDriftingDeath card) {
        super(card);
    }

    @Override
    public SilumgarTheDriftingDeath copy() {
        return new SilumgarTheDriftingDeath(this);
    }
}

class UnboostCreaturesTargetPlayerEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public UnboostCreaturesTargetPlayerEffect(int power, int toughness) {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "creatures defending player controls get -1/-1 until end of turn";
    }

    public UnboostCreaturesTargetPlayerEffect(final UnboostCreaturesTargetPlayerEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public UnboostCreaturesTargetPlayerEffect copy() {
        return new UnboostCreaturesTargetPlayerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(power);
                permanent.addToughness(toughness);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
