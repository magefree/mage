package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TombOfHorrorsAdventurer extends CardImpl {

    public TombOfHorrorsAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Tomb of Horrors Adventurer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Whenever you cast your second spell each turn, copy it. If you've completed a dungeon, copy that spell twice instead. You may choose new targets for the copies.
        this.addAbility(new CastSecondSpellTriggeredAbility(new TombOfHorrorsAdventurerEffect())
                .addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private TombOfHorrorsAdventurer(final TombOfHorrorsAdventurer card) {
        super(card);
    }

    @Override
    public TombOfHorrorsAdventurer copy() {
        return new TombOfHorrorsAdventurer(this);
    }
}

class TombOfHorrorsAdventurerEffect extends OneShotEffect {

    TombOfHorrorsAdventurerEffect() {
        super(Outcome.Benefit);
        staticText = "copy it. If you've completed a dungeon, copy that spell twice instead. " +
                "You may choose new targets for the copies";
    }

    private TombOfHorrorsAdventurerEffect(final TombOfHorrorsAdventurerEffect effect) {
        super(effect);
    }

    @Override
    public TombOfHorrorsAdventurerEffect copy() {
        return new TombOfHorrorsAdventurerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), true,
                CompletedDungeonWatcher.checkPlayer(source.getControllerId(), game) ? 2 : 1
        );
        return true;
    }
}
