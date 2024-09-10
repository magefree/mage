package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.ArtifactEnteredUnderYourControlCondition;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AkalPakalFirstAmongEquals extends CardImpl {

    public AkalPakalFirstAmongEquals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // At the beginning of each player's end step, if an artifact entered the battlefield under your control this turn, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new LookLibraryAndPickControllerEffect(2, 1, PutCards.HAND, PutCards.GRAVEYARD),
                TargetController.EACH_PLAYER,
                ArtifactEnteredUnderYourControlCondition.instance,
                false
        );
        ability.addHint(new ConditionHint(ArtifactEnteredUnderYourControlCondition.instance));
        this.addAbility(ability, new ArtifactEnteredControllerWatcher());
    }

    private AkalPakalFirstAmongEquals(final AkalPakalFirstAmongEquals card) {
        super(card);
    }

    @Override
    public AkalPakalFirstAmongEquals copy() {
        return new AkalPakalFirstAmongEquals(this);
    }
}
