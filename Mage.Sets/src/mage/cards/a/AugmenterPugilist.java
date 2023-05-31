package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class AugmenterPugilist extends ModalDoubleFacedCard {

    public AugmenterPugilist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TROLL, SubType.DRUID}, "{1}{G}{G}",
                "Echoing Equation", new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}{U}{U}");

        // 1.
        // Augmenter Pugilist
        // Creature — Troll Druid
        this.getLeftHalfCard().setPT(3, 3);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // As long as you control eight or more lands, Augmenter Pugilist gets +5/+5.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(
                    new BoostSourceEffect(
                            5, 5, Duration.WhileOnBattlefield
                    ),
                    new PermanentsOnTheBattlefieldCondition(
                            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                            ComparisonType.MORE_THAN, 7
                    ),
                    "as long as you control eight or more lands, {this} gets +5/+5"
            )
        ).addHint(LandsYouControlHint.instance));

        // 2.
        // Echoing Equation
        // Sorcery

        // Choose target creature you control. Each other creature you control becomes a copy of it until end of turn, except those creatures aren’t legendary if the chosen creature is legendary.
        this.getRightHalfCard().getSpellAbility().addEffect(new EchoingEquationEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

    }

    private AugmenterPugilist(final AugmenterPugilist card) {
        super(card);
    }

    @Override
    public AugmenterPugilist copy() {
        return new AugmenterPugilist(this);
    }
}

class EchoingEquationEffect extends OneShotEffect {

    public EchoingEquationEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control. Each other creature you control becomes a copy of it until end of turn, except those creatures aren't legendary if the chosen creature is legendary";
    }

    EchoingEquationEffect(EchoingEquationEffect effect) {
        super(effect);
    }

    @Override
    public EchoingEquationEffect copy() {
        return new EchoingEquationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyFrom = game.getPermanent(source.getFirstTarget());
        if (copyFrom != null) {
            game.getBattlefield().getAllActivePermanents(source.getControllerId()).stream()
                .filter(permanent -> permanent.isCreature(game) && !permanent.getId().equals(copyFrom.getId()))
                .forEach(copyTo -> game.copyPermanent(Duration.EndOfTurn, copyFrom, copyTo.getId(), source, new CopyApplier() {
                    @Override
                    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
                        blueprint.removeSuperType(SuperType.LEGENDARY);
                        return true;
                    }
                }));
            return true;
        }
        return false;
    }
}