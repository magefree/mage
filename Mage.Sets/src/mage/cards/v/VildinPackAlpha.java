
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class VildinPackAlpha extends CardImpl {

    public VildinPackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        this.transformable = true;
        this.nightCard = true;

        // Whenever a Werewolf enters the battlefield under your control, you may transform it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new VildinPackAlphaEffect(), new FilterCreaturePermanent(SubType.WEREWOLF, "a Werewolf"), true, SetTargetPointer.PERMANENT, null));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Vildin-Pack Alpha.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));

    }

    public VildinPackAlpha(final VildinPackAlpha card) {
        super(card);
    }

    @Override
    public VildinPackAlpha copy() {
        return new VildinPackAlpha(this);
    }
}

class VildinPackAlphaEffect extends OneShotEffect {

    public VildinPackAlphaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may transform it";
    }

    public VildinPackAlphaEffect(final VildinPackAlphaEffect effect) {
        super(effect);
    }

    @Override
    public VildinPackAlphaEffect copy() {
        return new VildinPackAlphaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent werewolf = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (werewolf != null && werewolf.isTransformable()) {
                werewolf.transform(game);
            }
            return true;
        }
        return false;
    }
}
