package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LydiaFrye extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public LydiaFrye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lydia Frye can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // At the beginning of your end step, surveil X, where X is the number of tapped Assassins you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LydiaFryeEffect(), TargetController.YOU, false
        ).addHint(LydiaFryeEffect.getHint()));
    }

    private LydiaFrye(final LydiaFrye card) {
        super(card);
    }

    @Override
    public LydiaFrye copy() {
        return new LydiaFrye(this);
    }
}

class LydiaFryeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ASSASSIN);

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Hint hint = new ValueHint(
            "Tapped Assassins you control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    LydiaFryeEffect() {
        super(Outcome.Benefit);
        staticText = "surveil X, where X is the number of tapped Assassins you control";
    }

    private LydiaFryeEffect(final LydiaFryeEffect effect) {
        super(effect);
    }

    @Override
    public LydiaFryeEffect copy() {
        return new LydiaFryeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        return player != null && count > 0 && player.surveil(count, source, game);
    }
}
