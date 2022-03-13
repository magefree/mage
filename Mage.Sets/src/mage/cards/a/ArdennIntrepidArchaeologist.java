package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPermanentOrPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdennIntrepidArchaeologist extends CardImpl {

    public ArdennIntrepidArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may attach any number of Auras and Equipment you control to target permanent or player.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new ArdennIntrepidArchaeologistEffect(), TargetController.YOU, true
        );
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ArdennIntrepidArchaeologist(final ArdennIntrepidArchaeologist card) {
        super(card);
    }

    @Override
    public ArdennIntrepidArchaeologist copy() {
        return new ArdennIntrepidArchaeologist(this);
    }
}

class ArdennIntrepidArchaeologistEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("Auras and Equipment you control");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    ArdennIntrepidArchaeologistEffect() {
        super(Outcome.Benefit);
        staticText = "attach any number of Auras and Equipment you control to target permanent or player";
    }

    private ArdennIntrepidArchaeologistEffect(final ArdennIntrepidArchaeologistEffect effect) {
        super(effect);
    }

    @Override
    public ArdennIntrepidArchaeologistEffect copy() {
        return new ArdennIntrepidArchaeologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || (player == null && permanent == null)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        controller.choose(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            if (player != null) {
                player.addAttachment(targetId, source, game);
            } else if (permanent != null) {
                permanent.addAttachment(targetId, source, game);
            }
        }
        return true;
    }
}
