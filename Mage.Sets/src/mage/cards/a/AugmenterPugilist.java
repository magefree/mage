package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class AugmenterPugilist extends ModalDoubleFacesCard {

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
        this.getLeftHalfCard().addAbility(new AugmenterPugilistAbility());

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

class AugmenterPugilistAbility extends SimpleStaticAbility {

    public AugmenterPugilistAbility() {
        super(new BoostSourceEffect(5, 5, Duration.WhileOnBattlefield));
    }

    @Override
    public boolean checkIfClause(Game game) {
        return game.getBattlefield().getAllPermanents().stream()
            .filter(permanent -> permanent.isLand() && permanent.isControlledBy(getControllerId()))
            .count() > 7;
    }

    @Override
    public String getRule() {
        return "as long as you control eight or more lands, {this} gets +5/+5";
    }
}

class EchoingEquationEffect extends ContinuousEffectImpl {

    public EchoingEquationEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "choose target creature you control. Each other creature you control becomes a copy of it until end of turn, except those creatures aren’t legendary if the chosen creature is legendary";
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
        Permanent target = game.getPermanent(source.getFirstTarget());
        game.getBattlefield().getAllPermanents().stream()
            .filter(permanent -> permanent.isCreature() && permanent.isControlledBy(source.getControllerId()))
            .forEach(creature -> source.addEffect(new CopyEffect(Duration.EndOfTurn, target, creature.getId(), false)));
        return true;
    }
}