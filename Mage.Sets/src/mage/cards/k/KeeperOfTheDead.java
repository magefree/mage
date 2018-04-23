/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author spjspj
 */
public class KeeperOfTheDead extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer();
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("nonblack creature");
    private static final FilterCard filter3 = new FilterCard("creature cards");

    static {
        filter.add(new KeeperOfDeadPredicate());
        filter2.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter3.add(new CardTypePredicate(CardType.CREATURE));
    }

    public KeeperOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {B}, {T}: Choose target opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability. Destroy target nonblack creature he or she controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KeeperOfTheDeadEffect(), new TapSourceCost());
        ability.addCost(new ManaCostsImpl("{B}"));
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        ability.addTarget(new KeeperOfTheDeadCreatureTarget());
        this.addAbility(ability);
    }

    public KeeperOfTheDead(final KeeperOfTheDead card) {
        super(card);
    }

    @Override
    public KeeperOfTheDead copy() {
        return new KeeperOfTheDead(this);
    }
}

class KeeperOfDeadPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    private static final FilterCard filter = new FilterCard("creature cards");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Permanent sourceObject = game.getPermanent(input.getSourceId());
        Player controller = null;
        if (sourceObject != null) {
            controller = game.getPlayer(sourceObject.getControllerId());
        }

        if (targetPlayer == null
                || controller == null
                || !controller.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int countGraveyardTargetPlayer = targetPlayer.getGraveyard().getCards(filter, game).size();
        int countGraveyardController = controller.getGraveyard().getCards(filter, game).size();
        return countGraveyardController >= countGraveyardTargetPlayer + 2;
    }

    @Override
    public String toString() {
        return "opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability";
    }
}

class KeeperOfTheDeadCreatureTarget extends TargetPermanent {

    private static final FilterCreaturePermanent nonblackCreaturefilter = new FilterCreaturePermanent("nonblack creature");

    static {
        nonblackCreaturefilter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public KeeperOfTheDeadCreatureTarget() {
        super(1, 1, new FilterCreaturePermanent("nonblack creature that player controls"), false);
    }

    public KeeperOfTheDeadCreatureTarget(final KeeperOfTheDeadCreatureTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = source.getFirstTarget();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.getControllerId().equals(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(sourceId);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(sourceId)) {
                object = item;
            }
            if (item.getSourceId().equals(sourceId)) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && nonblackCreaturefilter.match(permanent, game) && permanent.getControllerId().equals(playerId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public KeeperOfTheDeadCreatureTarget copy() {
        return new KeeperOfTheDeadCreatureTarget(this);
    }
}

class KeeperOfTheDeadEffect extends OneShotEffect {

    public KeeperOfTheDeadEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target nonblack creature contolled by target opponent who had at least two fewer creature cards in their graveyard than you did as you activated this ability";
    }

    public KeeperOfTheDeadEffect(final KeeperOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public KeeperOfTheDeadEffect copy() {
        return new KeeperOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());

        if (opponent != null) {
            Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (creature != null) {
                creature.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
