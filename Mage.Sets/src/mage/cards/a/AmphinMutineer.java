package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SalamnderWarriorToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AmphinMutineer extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Salamander creature");

    static {
        filter.add(Predicates.not(SubType.SALAMANDER.getPredicate()));
    }

    public AmphinMutineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Amphin Mutineer enters the battlefield, exile up to one target non-Salamander creature. That creature's controller creates a 4/3 blue Salamander Warrior creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AmphinMutineerEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // Encore {4}{U}{U}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{4}{U}{U}")));
    }

    private AmphinMutineer(final AmphinMutineer card) {
        super(card);
    }

    @Override
    public AmphinMutineer copy() {
        return new AmphinMutineer(this);
    }
}

class AmphinMutineerEffect extends OneShotEffect {

    AmphinMutineerEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target non-Salamander creature. " +
                "That creature's controller creates a 4/3 blue Salamander Warrior creature token";
    }

    private AmphinMutineerEffect(final AmphinMutineerEffect effect) {
        super(effect);
    }

    @Override
    public AmphinMutineerEffect copy() {
        return new AmphinMutineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        new SalamnderWarriorToken().putOntoBattlefield(1, game, source, player.getId());
        return true;
    }
}
