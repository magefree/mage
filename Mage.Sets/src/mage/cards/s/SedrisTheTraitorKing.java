
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public final class SedrisTheTraitorKing extends CardImpl {

    public SedrisTheTraitorKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Each creature card in your graveyard has unearth {2}{B}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SedrisTheTraitorKingEffect()));
    }

    private SedrisTheTraitorKing(final SedrisTheTraitorKing card) {
        super(card);
    }

    @Override
    public SedrisTheTraitorKing copy() {
        return new SedrisTheTraitorKing(this);
    }
}

class SedrisTheTraitorKingEffect extends ContinuousEffectImpl {
    SedrisTheTraitorKingEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each creature card in your graveyard has unearth {2}{B}";
    }

    SedrisTheTraitorKingEffect(final SedrisTheTraitorKingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID cardId : controller.getGraveyard()) {
                Card card = game.getCard(cardId);
                if (card != null && card.isCreature(game)) {
                    UnearthAbility ability = new UnearthAbility(new ManaCostsImpl<>("{2}{B}"));
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SedrisTheTraitorKingEffect copy() {
        return new SedrisTheTraitorKingEffect(this);
    }
}
