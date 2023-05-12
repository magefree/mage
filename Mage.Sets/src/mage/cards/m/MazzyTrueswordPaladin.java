package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MazzyTrueswordPaladin extends CardImpl {

    private static final FilterEnchantmentPermanent filter
            = new FilterEnchantmentPermanent("an Aura you control");

    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MazzyTrueswordPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever an enchanted creature attacks one of your opponents, it gets +2/+0 and gains trample until end of turn.
        this.addAbility(new MazzyAttackTriggeredAbility());

        // Whenever an Aura you control is put into your graveyard from the battlefield, exile it. Until the end of your next turn, you may cast that card.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new MazzyExileEffect(), false, filter, true, true));
    }

    private MazzyTrueswordPaladin(final MazzyTrueswordPaladin card) {
        super(card);
    }

    @Override
    public MazzyTrueswordPaladin copy() {
        return new MazzyTrueswordPaladin(this);
    }
}

class MazzyAttackTriggeredAbility extends AttacksAllTriggeredAbility {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("enchanted creature");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    MazzyAttackTriggeredAbility() {
        super(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("it gets +2/+0 and gains trample until end of turn"), false, filter, SetTargetPointer.PERMANENT, false);
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
    }

    MazzyAttackTriggeredAbility(final MazzyAttackTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Player defender = game.getPlayer(event.getTargetId());
            if (defender == null) {
                return false;
            }
            Set<UUID> opponents = game.getOpponents(this.getControllerId());
            if (opponents != null && opponents.contains(defender.getId())) {
                getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an enchanted creature attacks one of your opponents, it gets +2/+0 and gains trample until end of turn.";
    }

    @Override
    public MazzyAttackTriggeredAbility copy() {
        return new MazzyAttackTriggeredAbility(this);
    }
}


class MazzyExileEffect extends OneShotEffect {

    public MazzyExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. Until the end of your next turn, you may cast that card.";
    }

    private MazzyExileEffect(final MazzyExileEffect effect) {
        super(effect);
    }

    @Override
    public MazzyExileEffect copy() {
        return new MazzyExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card aura = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (aura == null) {
            return false;
        }
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, aura, TargetController.YOU,
                Duration.UntilEndOfYourNextTurn,
                false, false, true
        );
        return true;
    }
}
