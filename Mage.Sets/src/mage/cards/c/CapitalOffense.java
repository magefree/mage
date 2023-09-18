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
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<String> getRules() {
        return prepareLowerCaseRules(super.getRules());
    }

    @Override
    public List<String> getRules(Game game) {
        return prepareLowerCaseRules(super.getRules(game));
    }

    private List<String> prepareLowerCaseRules(List<String> rules) {
        return rules.stream()
                .map(s -> s.toLowerCase(Locale.ENGLISH))
                .collect(Collectors.toList());
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

        List<CardInfo> cardsInfo = CardRepository.instance.findCards(permanent.getName(), 1);
        if (cardsInfo.isEmpty()) {
            return 0;
        }

        Card card = cardsInfo.get(0).getCard();
        if (card == null) {
            return 0;
        }

        String originalRules = card.getRules(game)
                .stream()
                .map(r -> r.replaceAll("(?i)<i.*?</i>", ""))
                .map(r -> r.replaceAll("\\{this\\}", permanent.getName()))
                .collect(Collectors.joining("; "));
        String nonCapitalRules = originalRules.replaceAll("[A-Z]", "");
        return -1 * (originalRules.length() - nonCapitalRules.length());
    }

    @Override
    public String getMessage() {
        return "";
    }
}
