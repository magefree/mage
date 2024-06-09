package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class AquaticAlchemist extends AdventureCard {

    public AquaticAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{U}", "Bubble Up", "{2}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast your first instant or sorcery spell each turn, Aquatic Alchemist gets +2/+0 until end of turn.
        this.addAbility(new AquaticAlchemistTriggeredAbility());

        // Bubble up
        // Put target instant or sorcery card from your graveyard on top of your library.
        this.getSpellCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));

        this.finalizeAdventure();
    }

    private AquaticAlchemist(final AquaticAlchemist card) {
        super(card);
    }

    @Override
    public AquaticAlchemist copy() {
        return new AquaticAlchemist(this);
    }
}

class AquaticAlchemistTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterInstantOrSorcerySpell filter = new FilterInstantOrSorcerySpell();

    AquaticAlchemistTriggeredAbility() {
        super(new BoostSourceEffect(2, 0, Duration.EndOfTurn), filter, false);
        this.setTriggerPhrase("whenever you cast your first instant or sorcery spell each turn, ");
    }

    private AquaticAlchemistTriggeredAbility(final AquaticAlchemistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AquaticAlchemistTriggeredAbility copy() {
        return new AquaticAlchemistTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return isFirstInstantOrSorceryCastByPlayerOnTurn(spell, game);
    }

    private boolean isFirstInstantOrSorceryCastByPlayerOnTurn(Spell spell, Game game) {
        if (spell != null) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> eligibleSpells = watcher.getSpellsCastThisTurn(this.getControllerId())
                        .stream()
                        .filter(spell1 -> spell1.isInstantOrSorcery(game))
                        .collect(Collectors.toList());
                return eligibleSpells.size() == 1 && eligibleSpells.get(0).getId().equals(spell.getId());
            }
        }
        return false;
    }
}
