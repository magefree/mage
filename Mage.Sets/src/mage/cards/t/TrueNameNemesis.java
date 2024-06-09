package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 * Protection from a player is a new variant of the protection ability. It means
 * the following: -- True-Name Nemesis can't be the target of spells or
 * abilities controlled by the chosen player. -- True-Name Nemesis can't be
 * enchanted by Auras or equipped by Equipment controlled by the chosen player.
 * (The same is true for Fortifications controlled by the chosen player, if
 * True-Name Nemesis becomes a land.) -- True-Name Nemesis can't be blocked by
 * creatures controlled by the chosen player. -- All damage that would be dealt
 * to True-Name Nemesis by sources controlled by the chosen player is prevented.
 * (The same is true for sources owned by the chosen player that don't have
 * controllers.)
 *
 * @author LevelX2
 */
public final class TrueNameNemesis extends CardImpl {

    public TrueNameNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As True-Name Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Detriment)));

        // True-Name Nemesis has protection from the chosen player.
        this.addAbility(new ProtectionFromPlayerAbility());

    }

    private TrueNameNemesis(final TrueNameNemesis card) {
        super(card);
    }

    @Override
    public TrueNameNemesis copy() {
        return new TrueNameNemesis(this);
    }
}

class ProtectionFromPlayerAbility extends ProtectionAbility {

    public ProtectionFromPlayerAbility() {
        super(new FilterCard());
    }

    private ProtectionFromPlayerAbility(final ProtectionFromPlayerAbility ability) {
        super(ability);
    }

    @Override
    public ProtectionFromPlayerAbility copy() {
        return new ProtectionFromPlayerAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} has protection from the chosen player.";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        UUID playerId = (UUID) game.getState().getValue(this.getSourceId() + "_player");
        if (playerId != null && source != null) {
            if (source instanceof Permanent) {
                return !((Permanent) source).isControlledBy(playerId);
            }
            if (source instanceof Spell) {
                return !((Spell) source).isControlledBy(playerId);
            }
            if (source instanceof StackObject) {
                return !((StackObject) source).isControlledBy(playerId);
            }
            if (source instanceof Card) { // e.g. for Vengeful Pharaoh
                return !((Card) source).isOwnedBy(playerId);
            }
        }
        return true;
    }
}
