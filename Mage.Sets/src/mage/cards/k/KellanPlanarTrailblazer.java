package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class KellanPlanarTrailblazer extends CardImpl {

    public KellanPlanarTrailblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{R}: If Kellan is a Scout, it becomes a Human Faerie Detective and gains "Whenever Kellan deals combat damage to a player, exile the top card of your library. You may play that card this turn."
        this.addAbility(new SimpleActivatedAbility(
                new KellanPlanarTrailblazerDetectiveEffect(), new ManaCostsImpl<>("{1}{R}")
        ));

        // {2}{R}: If Kellan is a Detective, it becomes a 3/2 Human Faerie Rogue and gains double strike.
        this.addAbility(new SimpleActivatedAbility(
                new KellanPlanarTrailblazerRogueEffect(), new ManaCostsImpl<>("{2}{R}")
        ));
    }

    private KellanPlanarTrailblazer(final KellanPlanarTrailblazer card) {
        super(card);
    }

    @Override
    public KellanPlanarTrailblazer copy() {
        return new KellanPlanarTrailblazer(this);
    }
}

class KellanPlanarTrailblazerDetectiveEffect extends OneShotEffect {

    KellanPlanarTrailblazerDetectiveEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Scout, it becomes a Human Faerie Detective and gains "
                + "\"Whenever {this} deals combat damage to a player, exile the top card of your library. "
                + "You may play that card this turn.\"";
    }

    private KellanPlanarTrailblazerDetectiveEffect(KellanPlanarTrailblazerDetectiveEffect effect) {
        super(effect);
    }

    @Override
    public KellanPlanarTrailblazerDetectiveEffect copy() {
        return new KellanPlanarTrailblazerDetectiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.SCOUT, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.FAERIE, SubType.DETECTIVE
        ), source);
        game.addEffect(new GainAbilitySourceEffect(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                        .withTextOptions("that card", true)
        ), Duration.Custom), source);
        return true;
    }
}

class KellanPlanarTrailblazerRogueEffect extends OneShotEffect {

    KellanPlanarTrailblazerRogueEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Detective, it becomes a 3/2 Human Faerie Rogue " +
                "and gains double strike.";
    }

    private KellanPlanarTrailblazerRogueEffect(final KellanPlanarTrailblazerRogueEffect effect) {
        super(effect);
    }

    @Override
    public KellanPlanarTrailblazerRogueEffect copy() {
        return new KellanPlanarTrailblazerRogueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.DETECTIVE, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
                Duration.Custom, SubType.HUMAN, SubType.FAERIE, SubType.ROGUE
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                3, 2, Duration.Custom
        ), source);
        game.addEffect(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.Custom
        ), source);
        return true;
    }
}
