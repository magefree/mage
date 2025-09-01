package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeatrixLoyalGeneral extends CardImpl {

    public BeatrixLoyalGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on your turn, you may attach any number of Equipment you control to target creature you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BeatrixLoyalGeneralEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BeatrixLoyalGeneral(final BeatrixLoyalGeneral card) {
        super(card);
    }

    @Override
    public BeatrixLoyalGeneral copy() {
        return new BeatrixLoyalGeneral(this);
    }
}

class BeatrixLoyalGeneralEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);

    BeatrixLoyalGeneralEffect() {
        super(Outcome.Benefit);
        staticText = "you may attach any number of Equipment you control to target creature you control";
    }

    private BeatrixLoyalGeneralEffect(final BeatrixLoyalGeneralEffect effect) {
        super(effect);
    }

    @Override
    public BeatrixLoyalGeneralEffect copy() {
        return new BeatrixLoyalGeneralEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            permanent.addAttachment(targetId, source, game);
        }
        return true;
    }
}
