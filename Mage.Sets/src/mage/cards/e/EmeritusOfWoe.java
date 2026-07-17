package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class EmeritusOfWoe extends PrepareCard {

    public EmeritusOfWoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}", "Demonic Tutor", new CardType[]{CardType.SORCERY}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE, SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // At the beginning of your end step, if two or more creatures died this turn, this creature becomes prepared.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new BecomePreparedSourceEffect(), false, EmeritusOfWoeCondition.instance
        ));

        // Demonic Tutor
        // Sorcery {1}{B}
        // Search your library for a card, put that card into your hand, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true));
    }

    private EmeritusOfWoe(final EmeritusOfWoe card) {
        super(card);
    }

    @Override
    public EmeritusOfWoe copy() {
        return new EmeritusOfWoe(this);
    }
}

enum EmeritusOfWoeCondition implements Condition {
    instance;

    @Override
    public boolean apply (Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurn() >= 2;
        }
        return false;
    }
}
