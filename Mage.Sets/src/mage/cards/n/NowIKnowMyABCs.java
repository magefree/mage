
package mage.cards.n;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class NowIKnowMyABCs extends CardImpl {

    public NowIKnowMyABCs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{U}");

        // At the beginning of your upkeep, if you control permanents with names that include all twenty-six letters of the English alphabet, you win the game.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new NowIKnowMyABCsCondition(),
                "At the beginning of your upkeep, if you control permanents with names that include all twenty-six letters of the English alphabet, you win the game."));
    }

    private NowIKnowMyABCs(final NowIKnowMyABCs card) {
        super(card);
    }

    @Override
    public NowIKnowMyABCs copy() {
        return new NowIKnowMyABCs(this);
    }
}

class NowIKnowMyABCsCondition implements Condition {

    public NowIKnowMyABCsCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Character> letters = new HashSet<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            String permName = permanent.getName();
            for (int i = 0; i < permName.length(); i++) {
                Character letter = permName.charAt(i);
                if (Character.isLetter(letter)) {
                    letters.add(Character.toUpperCase(letter));
                }
            }
        }
        return letters.size() >= 26;
    }

    @Override
    public String toString() {
        return "if you control permanents with names that include all twenty-six letters of the English alphabet";
    }
}
