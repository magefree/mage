package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzulaOnTheHunt extends CardImpl {

    public AzulaOnTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Firebending 2
        this.addAbility(new FirebendingAbility(2));

        // Whenever Azula attacks, you lose 1 life and create a Clue token.
        Ability ability = new AttacksTriggeredAbility(new LoseLifeSourceControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("and"));
        this.addAbility(ability);
    }

    private AzulaOnTheHunt(final AzulaOnTheHunt card) {
        super(card);
    }

    @Override
    public AzulaOnTheHunt copy() {
        return new AzulaOnTheHunt(this);
    }
}
