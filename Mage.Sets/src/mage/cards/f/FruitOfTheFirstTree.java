
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FruitOfTheFirstTree extends CardImpl {

    public FruitOfTheFirstTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, you gain X life and draw X cards, where X is its toughness.
        this.addAbility( new DiesAttachedTriggeredAbility(new FruitOfTheFirstTreeEffect(), "enchanted creature"));
    }

    private FruitOfTheFirstTree(final FruitOfTheFirstTree card) {
        super(card);
    }

    @Override
    public FruitOfTheFirstTree copy() {
        return new FruitOfTheFirstTree(this);
    }
}

class FruitOfTheFirstTreeEffect extends OneShotEffect {

    public FruitOfTheFirstTreeEffect() {
        super(Outcome.Benefit);
        this.staticText = "you gain X life and draw X cards, where X is its toughness";
    }

    public FruitOfTheFirstTreeEffect(FruitOfTheFirstTreeEffect copy) {
        super(copy);
    }

    @Override
    public FruitOfTheFirstTreeEffect copy() {
        return new FruitOfTheFirstTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = (Permanent) getValue("attachedTo");
        if (controller != null && creature != null) {
            controller.gainLife(creature.getToughness().getValue(), game, source);
            controller.drawCards(creature.getToughness().getValue(), source, game);
            return true;            
        }
        return false;
    }
}
