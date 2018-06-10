
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.constants.CardType;
import mage.cards.repository.CardRepository;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class CapitalOffense extends CardImpl {

    public CapitalOffense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // target creature gets -x/-x until end of turn, where x is the number of times a capital letter appears in its rules text. (ignore reminder text and flavor text.)
        DynamicValue xValue = new NumberOfCapitalsInTextOfTargetCreatureCount();
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public CapitalOffense(final CapitalOffense card) {
        super(card);
    }

    @Override
    public CapitalOffense copy() {
        return new CapitalOffense(this);
    }
}

class NumberOfCapitalsInTextOfTargetCreatureCount implements DynamicValue {

    @Override
    public NumberOfCapitalsInTextOfTargetCreatureCount copy() {
        return new NumberOfCapitalsInTextOfTargetCreatureCount();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            int capitals = 0;
            List<CardInfo> cards = CardRepository.instance.findCards(permanent.getName());

            if (cards != null) {
                for (CardInfo cardInfo : cards) {
                    Card dummy = cardInfo != null ? cardInfo.getCard() : null;
                    if (dummy != null) {
                        for (String line : dummy.getRules()) {
                            line = line.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
                            line = line.replaceAll("\\{this\\}", permanent.getName());
                            capitals += line.length() - line.replaceAll("[A-Z]", "").length();
                        }
                    }
                    return -1 * capitals;
                }
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "target creature gets -x/-x until end of turn, where x is the number of times a capital letter appears in its rules text. (ignore reminder text and flavor text.)";
    }
}
