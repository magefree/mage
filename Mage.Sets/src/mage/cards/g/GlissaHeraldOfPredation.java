package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlissaHeraldOfPredation extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.PHYREXIAN);

    public GlissaHeraldOfPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, choose one --
        // * Incubate 2 twice.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GlissaHeraldOfPredationIncubateEffect(), TargetController.YOU, false
        );

        // * Transform all Incubator tokens you control.
        ability.addMode(new Mode(new GlissaHeraldOfPredationTransformEffect()));

        // * Phyrexians you control gain first strike and deathtouch until end of turn.
        ability.addMode(new Mode(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("Phyrexians you control gain first strike")).addEffect(
                new GainAbilityControlledEffect(
                        DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("and deathtouch until end of turn")
        ));
        this.addAbility(ability);
    }

    private GlissaHeraldOfPredation(final GlissaHeraldOfPredation card) {
        super(card);
    }

    @Override
    public GlissaHeraldOfPredation copy() {
        return new GlissaHeraldOfPredation(this);
    }
}

class GlissaHeraldOfPredationIncubateEffect extends OneShotEffect {

    GlissaHeraldOfPredationIncubateEffect() {
        super(Outcome.Benefit);
        staticText = "incubate 2 twice";
    }

    private GlissaHeraldOfPredationIncubateEffect(final GlissaHeraldOfPredationIncubateEffect effect) {
        super(effect);
    }

    @Override
    public GlissaHeraldOfPredationIncubateEffect copy() {
        return new GlissaHeraldOfPredationIncubateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        IncubateEffect.doIncubate(2, game, source);
        IncubateEffect.doIncubate(2, game, source);
        return true;
    }
}

class GlissaHeraldOfPredationTransformEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.INCUBATOR);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    GlissaHeraldOfPredationTransformEffect() {
        super(Outcome.Benefit);
        staticText = "transform all Incubator tokens you control";
    }

    private GlissaHeraldOfPredationTransformEffect(final GlissaHeraldOfPredationTransformEffect effect) {
        super(effect);
    }

    @Override
    public GlissaHeraldOfPredationTransformEffect copy() {
        return new GlissaHeraldOfPredationTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.transform(source, game);
        }
        return true;
    }
}
