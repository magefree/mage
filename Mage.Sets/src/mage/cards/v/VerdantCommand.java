package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.StackObject;
import mage.target.TargetPlayer;
import mage.target.TargetStackObject;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerdantCommand extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("loyalty ability of a planeswalker");

    static {
        filter.add(VerdantCommandPredicate.instance);
    }

    public VerdantCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target player creates two tapped 1/1 green Squirrel creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenTargetEffect(
                new SquirrelToken(), StaticValue.get(2), true, false
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // • Counter target loyalty ability of a planeswalker.
        Mode mode = new Mode(new CounterTargetEffect());
        mode.addTarget(new TargetStackObject(filter));
        this.getSpellAbility().addMode(mode);

        // • Exile target card from a graveyard.
        mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addMode(mode);

        // • Target player gains 3 life.
        mode = new Mode(new GainLifeTargetEffect(3));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private VerdantCommand(final VerdantCommand card) {
        super(card);
    }

    @Override
    public VerdantCommand copy() {
        return new VerdantCommand(this);
    }
}

enum VerdantCommandPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (!(input.getStackAbility() instanceof LoyaltyAbility)) {
            return false;
        }
        Permanent permanent = ((LoyaltyAbility) input.getStackAbility()).getSourcePermanentOrLKI(game);
        return permanent != null && permanent.isPlaneswalker(game);
    }
}
