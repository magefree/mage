
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.effects.WhileConditionContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class RamunapHydra extends CardImpl {

    public RamunapHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ramunap Hydra gets +1/+1 as long as you control a Desert.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(new FilterPermanent(SubType.DESERT, "Desert"), 1, 1)));

        // Ramunap Hydra gets +1/+1 as long as there is a Desert card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RamunapHydraBoostEffect(1, 1)).addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private RamunapHydra(final RamunapHydra card) {
        super(card);
    }

    @Override
    public RamunapHydra copy() {
        return new RamunapHydra(this);
    }
}

class RamunapHydraBoostEffect extends WhileConditionContinuousEffect {

    private static final FilterCard filter = new FilterCard("a Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    private final int power;
    private final int toughness;

    public RamunapHydraBoostEffect(int power, int toughness) {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, new CardsInControllerGraveyardCondition(1, filter), Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        staticText = "{this} gets +1/+1 as long as there is a Desert card in your graveyard";
    }

    public RamunapHydraBoostEffect(final RamunapHydraBoostEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public RamunapHydraBoostEffect copy() {
        return new RamunapHydraBoostEffect(this);
    }

    @Override
    public boolean applyEffect(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addPower(power);
            permanent.addToughness(toughness);
        }
        return true;
    }

}
