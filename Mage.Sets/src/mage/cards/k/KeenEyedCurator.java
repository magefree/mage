package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KeenEyedCurator extends CardImpl {

    public KeenEyedCurator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as there are four or more card types among cards exiled with Keen-Eyed Curator, it gets +4/+4 and has trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield),
                KeenEyedCuratorCondition.instance, "as long as there are four or " +
                "more card types among cards exiled with {this}, it gets +4/+4"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                KeenEyedCuratorCondition.instance, "and has trample"
        ));
        this.addAbility(ability.addHint(KeenEyedCuratorHint.instance));

        // {1}: Exile target card from a graveyard.
        ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private KeenEyedCurator(final KeenEyedCurator card) {
        super(card);
    }

    @Override
    public KeenEyedCurator copy() {
        return new KeenEyedCurator(this);
    }
}

enum KeenEyedCuratorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }
        // must use object's zcc cause static and activated abilities init in diff time with diff zcc (see #13022)
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        return exileZone != null && exileZone.getCards(game)
                .stream()
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .count() >= 4;
    }
}

enum KeenEyedCuratorHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        MageObject sourceObject = ability.getSourceObject(game);
        if (sourceObject == null) {
            return "Card types exiled: 0";
        }

        // must use object's zcc cause static and activated abilities init in diff time with diff zcc (see #13022)
        UUID exileId = CardUtil.getExileZoneId(game, ability.getSourceId(), sourceObject.getZoneChangeCounter(game));
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return "Card types exiled: 0";
        }

        List<String> types = exileZone.getCards(game).stream()
                    .map(card -> card.getCardType(game))
                    .flatMap(Collection::stream)
                    .distinct()
                    .map(CardType::toString)
                    .sorted()
                    .collect(Collectors.toList());
        String details = types.isEmpty() ? "" : " (" + String.join(", ", types) + ")";
        return "Card types exiled: " + types.size() + details;
    }

    @Override
    public Hint copy() {
        return this;
    }
}
