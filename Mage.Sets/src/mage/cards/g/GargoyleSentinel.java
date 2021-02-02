

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GargoyleSentinel extends CardImpl {

    public GargoyleSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GargoyleSentinelEffect(), new ManaCostsImpl("{3}")));
    }

    private GargoyleSentinel(final GargoyleSentinel card) {
        super(card);
    }

    @Override
    public GargoyleSentinel copy() {
        return new GargoyleSentinel(this);
    }

}

class GargoyleSentinelEffect extends ContinuousEffectImpl {

    public GargoyleSentinelEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
        staticText = "Until end of turn, {this} loses defender and gains flying";
    }

    public GargoyleSentinelEffect(final GargoyleSentinelEffect effect) {
        super(effect);
    }

    @Override
    public GargoyleSentinelEffect copy() {
        return new GargoyleSentinelEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.removeAbility(DefenderAbility.getInstance(), source.getSourceId(), game);
                        permanent.getAbilities().add(FlyingAbility.getInstance());
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
