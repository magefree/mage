package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class NogAmbitiousCadet extends CardImpl {

    public NogAmbitiousCadet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FERENGI);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // {1}: If Nog is a Rogue, he becomes a Ferengi Scout with base power and toughness 3/4 and gains "Whenever Nog becomes tapped, surveil 2."
        this.addAbility(new SimpleActivatedAbility(
            new NogAmbitiousCadetScoutEffect(),
            new GenericManaCost(1)
        ));

        // {3}: If Nog is a Scout, he becomes a Ferengi Officer and gains "You may play lands from your graveyard" and "You may play an additional land on each of your turns."
        this.addAbility(new SimpleActivatedAbility(
            new NogAmbitiousCadetOfficerEffect(),
            new GenericManaCost(3)
        ));
    }

    private NogAmbitiousCadet(final NogAmbitiousCadet card) {
        super(card);
    }

    @Override
    public NogAmbitiousCadet copy() {
        return new NogAmbitiousCadet(this);
    }
}

class NogAmbitiousCadetScoutEffect extends OneShotEffect {

    NogAmbitiousCadetScoutEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Rogue, he becomes a Ferengi Scout with base power and toughness 3/4 "
            + "and gains \"Whenever {this} becomes tapped, surveil 2.\"";
    }

    private NogAmbitiousCadetScoutEffect(final NogAmbitiousCadetScoutEffect effect) {
        super(effect);
    }

    @Override
    public NogAmbitiousCadetScoutEffect copy() {
        return new NogAmbitiousCadetScoutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.ROGUE, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
            Duration.Custom, SubType.FERENGI, SubType.SCOUT
        ), source);
        game.addEffect(new SetBasePowerToughnessSourceEffect(
            3, 4, Duration.Custom
        ), source);
        game.addEffect(new GainAbilitySourceEffect(new BecomesTappedSourceTriggeredAbility(
            new SurveilEffect(2)
        ), Duration.Custom), source);
        return true;
    }
}

class NogAmbitiousCadetOfficerEffect extends OneShotEffect {

    NogAmbitiousCadetOfficerEffect() {
        super(Outcome.Benefit);
        staticText = "if {this} is a Scout, he becomes a Ferengi Officer and gains "
            + "\"You may play lands from your graveyard\" and "
            + "\"You may play an additional land on each of your turns.\"";
    }

    private NogAmbitiousCadetOfficerEffect(final NogAmbitiousCadetOfficerEffect effect) {
        super(effect);
    }

    @Override
    public NogAmbitiousCadetOfficerEffect copy() {
        return new NogAmbitiousCadetOfficerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.hasSubtype(SubType.SCOUT, game)) {
            return false;
        }
        game.addEffect(new AddCardSubTypeSourceEffect(
            Duration.Custom, SubType.FERENGI, SubType.OFFICER
        ), source);
        game.addEffect(new GainAbilitySourceEffect(new SimpleStaticAbility(
            PlayFromGraveyardControllerEffect.playLands()
        ), Duration.Custom), source);
        game.addEffect(new GainAbilitySourceEffect(new SimpleStaticAbility(
            new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ), Duration.Custom), source);
        return true;
    }
}
