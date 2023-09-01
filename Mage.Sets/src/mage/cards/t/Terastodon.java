
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElephantToken;
import mage.target.TargetPermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class Terastodon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public Terastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When Terastodon enters the battlefield, you may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller creates a 3/3 green Elephant creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TerastodonEffect(), true);
        ability.addTarget(new TargetPermanent(0, 3, filter, false));
        this.addAbility(ability);
    }

    private Terastodon(final Terastodon card) {
        super(card);
    }

    @Override
    public Terastodon copy() {
        return new Terastodon(this);
    }
}

class TerastodonEffect extends OneShotEffect {

    public TerastodonEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "you may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller creates a 3/3 green Elephant creature token";
    }

    private TerastodonEffect(final TerastodonEffect effect) {
        super(effect);
    }

    @Override
    public TerastodonEffect copy() {
        return new TerastodonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> destroyedPermanents = new HashMap<>();
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source, game, false)) {
                    if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                        int numberPermanents = destroyedPermanents.getOrDefault(permanent.getControllerId(), 0);
                        destroyedPermanents.put(permanent.getControllerId(), numberPermanents + 1);
                    }
                }
            }
        }
        game.getState().processAction(game);
        ElephantToken elephantToken = new ElephantToken();
        for (Entry<UUID, Integer> entry : destroyedPermanents.entrySet()) {
            elephantToken.putOntoBattlefield(entry.getValue(), game, source, entry.getKey());
        }
        return true;
    }
}
