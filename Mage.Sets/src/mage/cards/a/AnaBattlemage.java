package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class AnaBattlemage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new KickedCostCondition("{2}{U}");
    private static final Condition condition2 = new KickedCostCondition("{1}{B}");

    public AnaBattlemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{U} and/or {1}{B}
        KickerAbility kickerAbility = new KickerAbility("{2}{U}");
        kickerAbility.addKickerCost("{1}{B}");
        this.addAbility(kickerAbility);

        // When Ana Battlemage enters the battlefield, if it was kicked with its {2}{U} kicker, target player discards three cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(3)).withInterveningIf(condition);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Ana Battlemage enters the battlefield, if it was kicked with its {1}{B} kicker, tap target untapped creature and that creature deals damage equal to its power to its controller.
        ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect()).withInterveningIf(condition2);
        ability.addEffect(new AnaBattlemageEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AnaBattlemage(final AnaBattlemage card) {
        super(card);
    }

    @Override
    public AnaBattlemage copy() {
        return new AnaBattlemage(this);
    }
}

class AnaBattlemageEffect extends OneShotEffect {

    AnaBattlemageEffect() {
        super(Outcome.Detriment);
        this.staticText = "and that creature deals damage equal to its power to its controller";
    }

    private AnaBattlemageEffect(final AnaBattlemageEffect effect) {
        super(effect);
    }

    @Override
    public AnaBattlemageEffect copy() {
        return new AnaBattlemageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }
        Player controller = game.getPlayer(targetCreature.getControllerId());
        return controller != null && controller.damage(
                targetCreature.getPower().getValue(), source.getSourceId(), source, game
        ) > 0;
    }
}
