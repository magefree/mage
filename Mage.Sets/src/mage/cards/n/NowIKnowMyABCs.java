package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author L_J
 */
public final class NowIKnowMyABCs extends CardImpl {

    public NowIKnowMyABCs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // At the beginning of your upkeep, if you control permanents with names that include all twenty-six letters of the English alphabet, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(NowIKnowMyABCsCondition.instance));
    }

    private NowIKnowMyABCs(final NowIKnowMyABCs card) {
        super(card);
    }

    @Override
    public NowIKnowMyABCs copy() {
        return new NowIKnowMyABCs(this);
    }
}

enum NowIKnowMyABCsCondition implements Condition {
    instance;

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
