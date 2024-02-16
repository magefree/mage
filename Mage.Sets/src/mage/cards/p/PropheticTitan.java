package mage.cards.p;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PropheticTitan extends CardImpl {

    public PropheticTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Delirium — When Prophetic Titan enters the battlefield, choose one. If there are four or more card types among cards in your graveyard, choose both.
        // • Prophetic Titan deals 4 damage to any target.
        // • Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new PropheticTitanTriggeredAbility());
    }

    private PropheticTitan(final PropheticTitan card) {
        super(card);
    }

    @Override
    public PropheticTitan copy() {
        return new PropheticTitan(this);
    }
}

class PropheticTitanTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public PropheticTitanTriggeredAbility() {
        super(new DamageTargetEffect(4), false);
        this.addMode(new Mode(new LookLibraryAndPickControllerEffect(
                4, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM)));
        this.getModes().setChooseText(
                "choose one. If there are four or more card types among cards in your graveyard, choose both instead."
        );
        this.addTarget(new TargetAnyTarget());
        this.addHint(CardTypesInGraveyardHint.YOU);
        this.setAbilityWord(AbilityWord.DELIRIUM);
    }

    private PropheticTitanTriggeredAbility(final PropheticTitanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PropheticTitanTriggeredAbility copy() {
        return new PropheticTitanTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        int modes = DeliriumCondition.instance.apply(game, this) ? 2 : 1;
        this.getModes().setMinModes(modes);
        this.getModes().setMaxModes(modes);
        return true;
    }
}
