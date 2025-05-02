package mage.cards.s;

import java.util.Collection;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FlurryAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author Jmlundeen
 */
public final class ShikoAndNarsetUnified extends CardImpl {

    public ShikoAndNarsetUnified(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Flurry -- Whenever you cast your second spell each turn, copy that spell if it targets a permanent or player, and you may choose new targets for the copy. If you don't copy a spell this way, draw a card.
        this.addAbility(new FlurryAbility(new ShikoAndNarsetEffect()));
    }

    private ShikoAndNarsetUnified(final ShikoAndNarsetUnified card) {
        super(card);
    }

    @Override
    public ShikoAndNarsetUnified copy() {
        return new ShikoAndNarsetUnified(this);
    }

}
class ShikoAndNarsetEffect extends OneShotEffect {

    public ShikoAndNarsetEffect() {
        super(Outcome.Copy);
        this.staticText = "copy that spell if it targets a permanent or player, and you may choose new targets for the copy. " +
                "If you don't copy a spell this way, draw a card.";
    }

    private ShikoAndNarsetEffect(final ShikoAndNarsetEffect effect) {
        super(effect);
    }

    @Override
    public ShikoAndNarsetEffect copy() {
        return new ShikoAndNarsetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) this.getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }

        boolean targetsPermOrPlayer = spell.getStackAbility().getTargets().stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(uuid -> game.getPlayer(uuid) != null || game.getPermanent(uuid) != null);
        if (targetsPermOrPlayer) {
            boolean newTargets = controller.chooseUse(Outcome.Neutral, "Choose new targets for the copy of " + spell.getLogName() + "?", source, game);
            spell.createCopyOnStack(game, source, source.getControllerId(), newTargets);
        }
        else {
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
