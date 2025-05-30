package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TifasLimitBreak extends CardImpl {

    public TifasLimitBreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Somersault -- {0} -- Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));
        this.getSpellAbility().withFirstModeFlavorWord("Somersault");

        // * Meteor Strikes -- {2} -- Double target creature's power and toughness until end of turn.
        this.getSpellAbility().addMode(new Mode(new TifasLimitBreakEffect(2))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new GenericManaCost(2))
                .withFlavorWord("Meteor Strikes"));

        // * Final Heaven -- {6}{G} -- Triple target creature's power and toughness until end of turn.
        this.getSpellAbility().addMode(new Mode(new TifasLimitBreakEffect(3))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new ManaCostsImpl<>("{6}{G}"))
                .withFlavorWord("Final Heaven"));
    }

    private TifasLimitBreak(final TifasLimitBreak card) {
        super(card);
    }

    @Override
    public TifasLimitBreak copy() {
        return new TifasLimitBreak(this);
    }
}

class TifasLimitBreakEffect extends OneShotEffect {

    private final int multiplier;

    TifasLimitBreakEffect(int multiplier) {
        super(Outcome.Benefit);
        staticText = makeWord(multiplier) + " target creature's power and toughness until end of turn";
        this.multiplier = multiplier;
    }

    private TifasLimitBreakEffect(final TifasLimitBreakEffect effect) {
        super(effect);
        this.multiplier = effect.multiplier;
    }

    @Override
    public TifasLimitBreakEffect copy() {
        return new TifasLimitBreakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(
                (multiplier - 1) * permanent.getPower().getValue(),
                (multiplier - 1) * permanent.getToughness().getValue()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }

    private static String makeWord(int multiplier) {
        switch (multiplier) {
            case 2:
                return "double";
            case 3:
                return "triple";
            default:
                throw new UnsupportedOperationException("no idea how we got here");
        }
    }
}
