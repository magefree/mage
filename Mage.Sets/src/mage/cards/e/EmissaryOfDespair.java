
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author wetterlicht
 */
public final class EmissaryOfDespair extends CardImpl {

    public EmissaryOfDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Emissary of Despair deals combat damage to a player, that player loses 1 life for each artifact they control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseLifeTargetEffect(EmissaryOfDespairCount.instance), false, true));
    }

    private EmissaryOfDespair(final EmissaryOfDespair card) {
        super(card);
    }

    @Override
    public EmissaryOfDespair copy() {
        return new EmissaryOfDespair(this);
    }
}

enum EmissaryOfDespairCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (effect.getTargetPointer().getFirst(game, sourceAbility) == null) {
            return 0;
        }
        FilterArtifactPermanent filter = new FilterArtifactPermanent();
        filter.add(new ControllerIdPredicate(effect.getTargetPointer().getFirst(game, sourceAbility)));
        return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
    }

    @Override
    public EmissaryOfDespairCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "artifact they control";
    }
}
