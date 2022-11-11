package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurgeEngine extends CardImpl {

    public SurgeEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {U}: Surge Engine loses defender and gains "This creature can't be blocked."
        Ability ability = new SimpleActivatedAbility(new LoseAbilitySourceEffect(
                DefenderAbility.getInstance(), Duration.Custom
        ), new ManaCostsImpl<>("{U}"));
        ability.addEffect(new GainAbilitySourceEffect(
                new CantBeBlockedSourceAbility(), Duration.Custom
        ).setText("and gains \"This creature can't be blocked.\""));
        this.addAbility(ability);

        // {2}{U}: Surge Engine becomes blue and has base power and toughness 5/4. Activate only if Surge Engine doesn't have defender.
        ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new BecomesColorSourceEffect(ObjectColor.BLUE, Duration.Custom),
                new ManaCostsImpl<>("{2}{U}"), SurgeEngineCondition.instance
        );
        ability.addEffect(new SetBasePowerToughnessSourceEffect(
                5, 4, Duration.Custom, SubLayer.SetPT_7b
        ).setText("and has base power and toughness 5/4"));
        this.addAbility(ability);

        // {4}{U}{U}: Draw three cards. Activate only if Surge Engine is blue and only once.
        this.addAbility(new SurgeEngineAbility());
    }

    private SurgeEngine(final SurgeEngine card) {
        super(card);
    }

    @Override
    public SurgeEngine copy() {
        return new SurgeEngine(this);
    }
}

enum SurgeEngineCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.hasAbility(DefenderAbility.getInstance(), game))
                .orElse(false);
    }

    @Override
    public String toString() {
        return "if {this} doesn't have defender";
    }
}

class SurgeEngineAbility extends ActivatedAbilityImpl {

    private static final Condition staticCondition = (game, source) -> Optional
            .ofNullable(source.getSourcePermanentIfItStillExists(game))
            .filter(Objects::nonNull)
            .map(permanent -> permanent.getColor(game))
            .map(ObjectColor::isBlue)
            .orElse(false);

    SurgeEngineAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new ManaCostsImpl<>("{4}{U}{U}"));
        this.condition = staticCondition;
        this.maxActivationsPerGame = 1;
    }

    private SurgeEngineAbility(final SurgeEngineAbility ability) {
        super(ability);
    }

    @Override
    public SurgeEngineAbility copy() {
        return new SurgeEngineAbility(this);
    }

    @Override
    public String getRule() {
        return "{4}{U}{U}: Draw three cards. Activate only if {this} is blue and only once.";
    }
}
