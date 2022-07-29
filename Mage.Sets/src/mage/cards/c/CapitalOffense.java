package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class CapitalOffense extends CardImpl {

    public CapitalOffense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // target creature gets -x/-x until end of turn, where x is the number of times a capital letter appears in its rules text. (ignore reminder text and flavor text.)
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                capitaloffensecount.instance, capitaloffensecount.instance, Duration.EndOfTurn
        ).setText("target creature gets -x/-x until end of turn, where x is the number of times " +
                "a capital letter appears in its rules text. <i>(ignore reminder text and flavor text.)</i>"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CapitalOffense(final CapitalOffense card) {
        super(card);
    }

    @Override
    public CapitalOffense copy() {
        return new CapitalOffense(this);
    }
}

enum capitaloffensecount implements DynamicValue {
    instance;

    @Override
    public capitaloffensecount copy() {
        return instance;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getTargets().get(0).getFirstTarget());
        if (permanent == null) {
            return 0;
        }
        int capitals = 0;
        List<CardInfo> cards = CardRepository.instance.findCards(permanent.getName());

        if (cards == null) {
            return 0;
        }
        for (CardInfo cardInfo : cards) {
            Card dummy = cardInfo != null ? cardInfo.getCard() : null;
            if (dummy == null) {
                return -1 * capitals;
            }
            for (String line : dummy.getRules()) {
                line = line.replaceAll("(?i)<i.*?</i>", ""); // Ignoring reminder text in italic
                line = line.replaceAll("\\{this\\}", permanent.getName());
                capitals += line.length() - line.replaceAll("[A-Z]", "").length();
            }
            return -1 * capitals;
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
