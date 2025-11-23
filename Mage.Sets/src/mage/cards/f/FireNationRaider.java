package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationRaider extends CardImpl {

    public FireNationRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Raid -- When this creature enters, if you attacked this turn, create a Clue token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken()))
                .withInterveningIf(RaidCondition.instance)
                .setAbilityWord(AbilityWord.RAID)
                .addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private FireNationRaider(final FireNationRaider card) {
        super(card);
    }

    @Override
    public FireNationRaider copy() {
        return new FireNationRaider(this);
    }
}
