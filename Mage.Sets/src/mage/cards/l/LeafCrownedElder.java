
package mage.cards.l;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class LeafCrownedElder extends CardImpl {

    public LeafCrownedElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Leaf-Crowned Elder, you may reveal it.
        // If you do, you may play that card without paying its mana cost.
        this.addAbility(new KinshipAbility(new LeafCrownedElderPlayEffect()));

    }

    private LeafCrownedElder(final LeafCrownedElder card) {
        super(card);
    }

    @Override
    public LeafCrownedElder copy() {
        return new LeafCrownedElder(this);
    }
}

class LeafCrownedElderPlayEffect extends OneShotEffect {

    public LeafCrownedElderPlayEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may play that card without paying its mana cost";
    }

    public LeafCrownedElderPlayEffect(final LeafCrownedElderPlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && card != null) {
            if (controller.chooseUse(Outcome.PlayForFree, "Play " + card.getIdName() + " without paying its mana cost?", source, game)) {
                controller.playCard(card, game, true, new ApprovingObject(source, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public LeafCrownedElderPlayEffect copy() {
        return new LeafCrownedElderPlayEffect(this);
    }

}
