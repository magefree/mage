package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class OriPlateStacker extends CardImpl {

    public OriPlateStacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Ori enters, destroy all artifacts and enchantments your opponents control. You gain 1 life for each permanent destroyed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OriPlateStackerEffect()));
    }

    private OriPlateStacker(final OriPlateStacker card) {
        super(card);
    }

    @Override
    public OriPlateStacker copy() {
        return new OriPlateStacker(this);
    }
}

class OriPlateStackerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OriPlateStackerEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all artifacts and enchantments your opponents control. You gain 1 life for each permanent destroyed this way";
    }

    private OriPlateStackerEffect(final OriPlateStackerEffect effect) {
        super(effect);
    }

    @Override
    public OriPlateStackerEffect copy() {
        return new OriPlateStackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int destroyedPermanents = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent.destroy(source, game, false)) {
                destroyedPermanents++;
            }
        }
        if (destroyedPermanents > 0) {
            new GainLifeEffect(destroyedPermanents).apply(game, source);
        }
        return true;
    }
}
