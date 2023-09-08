package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(3));
        ability.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new KickedCostCondition("{2}{U}"),
                "When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, target player discards three cards."));
        // When Ana Battlemage enters the battlefield, if it was kicked with its {1}{B} kicker, tap target untapped creature and that creature deals damage equal to its power to its controller.
        ability = new EntersBattlefieldTriggeredAbility(new AnaBattlemageKickerEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new KickedCostCondition("{1}{B}"),
                "When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, tap target untapped creature and that creature deals damage equal to its power to its controller."));
    }

    private AnaBattlemage(final AnaBattlemage card) {
        super(card);
    }

    @Override
    public AnaBattlemage copy() {
        return new AnaBattlemage(this);
    }
}

class AnaBattlemageKickerEffect extends OneShotEffect {

    public AnaBattlemageKickerEffect() {
        super(Outcome.Detriment);
        this.staticText = "tap target untapped creature and it deals damage equal to its power to its controller";
    }

    private AnaBattlemageKickerEffect(final AnaBattlemageKickerEffect effect) {
        super(effect);
    }

    @Override
    public AnaBattlemageKickerEffect copy() {
        return new AnaBattlemageKickerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            applied = targetCreature.tap(source, game);
            Player controller = game.getPlayer(targetCreature.getControllerId());
            if (controller != null) {
                controller.damage(targetCreature.getPower().getValue(), source.getSourceId(), source, game);
                applied = true;
            }
        }
        return applied;
    }
}
